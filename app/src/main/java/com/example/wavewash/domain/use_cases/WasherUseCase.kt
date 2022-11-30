package com.example.wavewash.domain.use_cases

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.data.local.order.OrderDao
import com.example.wavewash.data.local.order.OrderWithServices
import com.example.wavewash.data.local.order.toEntity
import com.example.wavewash.data.local.order.toOrder
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.local.service.toEntity
import com.example.wavewash.data.local.washer.*
import com.example.wavewash.data.remote.dto.order.OrderDto
import com.example.wavewash.data.remote.dto.order.toOrder
import com.example.wavewash.data.remote.dto.washer.WasherApi
import com.example.wavewash.data.remote.dto.washer.toWasher
import com.example.wavewash.domain.model.Order
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import com.example.wavewash.utils.getDateLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

private const val TAG = "Washer"

class WasherUseCase(
    private val washerApi: WasherApi,
    private val appDataStoreManager: AppDataStore,
    private val washerDao: WasherDao,
    private val orderDao: OrderDao,
    private val serviceDao: ServiceDao
) {

    fun addWasher(data: MultipartBody.Part, image: MultipartBody.Part?): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = washerApi.get_washCompany_id(token)[0]
                val washer = washerApi.add_washer(token, companyId, data, image).toWasher()
                washerDao.insertOrUpdateWasher(washer.toEntity())
                emit(Resource.Success("washer added remote $washer"))
            } catch (ex: Exception) {
                Log.d(TAG, "addWasher: Exception ${ex.message}")
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))

        }

    fun update(
        data: MultipartBody.Part,
        image: MultipartBody.Part?,
        washerId: Long
    ): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val washer = washerApi.update_washer(token, washerId, data, image).toWasher()
                Log.d(TAG, "update: remote $washer")
                washerDao.insertOrUpdateWasher(washer.toEntity())
                emit(Resource.Success("update"))
            } catch (ex: Exception) {
                emit(Resource.Error(message = ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun delete_washer(washerId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                washerApi.delete_washer(token, washerId)
                washerDao.deleteById(washerId)
                Log.d(TAG, "Washer deleted:")
                emit(Resource.Success("washer deleted"))
            } catch (ex: Exception) {
                Log.d(TAG, "delete_washer: ${ex.message}")
                emit(Resource.Error(message = ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun get_washers(name: String, page: Int): Flow<Resource<List<Washer>>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = washerApi.get_washCompany_id(token)[0]
                val washers = washerApi.get_washers(token, companyId, name, page).map {
                    it.toWasher()
                }
                val washerEntities = washers.map {
                    it.toEntity()
                }
                Log.d(TAG, "get_washers remote: $washers")
                washerDao.insertOrUpdateWashers(
                    washerEntities
                )
            } catch (ex: Exception) {
                Log.d(TAG, "get_washers: exception ${ex.message}")
                emit(Resource.Error(ex.message!!))
            }

            washerDao.getAllWashers(query = name, page = page + 1).collect {
                val washers = it.map { entity ->
                    entity.toWasher()
                }
                Log.d(TAG, "get_washers cached: $washers")
                emit(Resource.Success(washers))
            }

            emit(Resource.Loading(false))
        }

    fun get_washer(washerId: Long): Flow<Resource<Washer>> =
        flow {
            emit(Resource.Loading())
            try {
                washerDao.getWasherFlow(washerId).collect {
                    Log.d(TAG, "get_washer: cached $it")
                    emit(Resource.Success(it.toWasher()))
                }
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }

            emit(Resource.Loading(false))
        }

    fun get_washer_not_flow(washerId: Long): Flow<Resource<Washer>> =
        flow {
            emit(Resource.Loading())
            try {
                val washer = washerDao.getWasherNotFlow(washerId).toWasher()
                emit(Resource.Success(washer))
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }

            emit(Resource.Loading(false))
        }

    fun get_washer_orders(
        washerId: Long,
        isActive: Boolean,
        dateFrom: String,
        dateTo: String,
        page: Int
    ): Flow<Resource<List<Order>>> =
        flow {
            emit(Resource.Loading())
            val dateFromLong = getDateLong(dateFrom)
            val dateToLong = getDateLong(dateTo)
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result =
                    washerApi.get_washer_orders(
                        token,
                        washerId,
                        isActive,
                        dateFrom,
                        dateTo,
                        page
                    )
                result.forEach { orderDto ->
                    orderDao.insertOrUpdateOrder(orderDto.toOrder().toEntity())
                    orderDto.services.forEach { service ->
                        serviceDao.insertOrUpdateService(service.toEntity())
                        if (!orderDao.isRowOrderServiceCrossRefExist(orderDto.id, service.id)) {
                            orderDao.insertOrderServiceCrossRef(
                                OrderServiceCrossRef(orderDto.id, service.id)
                            )
                        }
                    }
                    orderDto.washers.forEach { washer ->
                        washerDao.insertOrUpdateWasher(washer.toEntity())
                        if (!orderDao.isRowOrderWasherCrossRefExist(orderDto.id, washer.id)) {
                            orderDao.insertOrderWasherCrossRef(
                                OrderWasherCrossRef(orderDto.id, washer.id)
                            )
                        }
                    }
                }
                Log.d(TAG, "get_washer_orders: remote $result")
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }
            washerDao.getOrdersOfWasher(washerId = washerId, page = page + 1).combine(
                washerDao.getWasherFlow(washerId)
            ) { ids, washer ->
                val ordersWithServicesJunction = orderDao.getOrdersWithServices(
                    page = page + 1,
                    dateFrom = dateFromLong,
                    dateTo = dateToLong,
                    isActive = isActive,
                    ids = ids
                )
                ordersWithServicesJunction.map {
                    it.toOrder(washer)
                }
            }.collect {
                Log.d(TAG, "get_washer_orders: cached $it")
                emit(Resource.Success(it))
            }

            emit(Resource.Loading(false))
        }

    fun get_washer_earnedStake(
        washerId: Long,
        dateFrom: String,
        dateTo: String,
    ): Flow<Resource<Long>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                washerDao.getAllOrdersOfWasher(washerId).combine(
                    washerDao.getWasherFlow(washerId)
                ) { ids, washer ->
                    washerApi.get_washer_earnedStake(token, washerId, dateFrom, dateTo)
                }.collect { sumOfStake ->
                    emit(Resource.Success(sumOfStake))
                }
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun get_washer_earnedMoney(
        washerId: Long,
        dateFrom: String,
        dateTo: String,
    ): Flow<Resource<Long>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                washerDao.getAllOrdersOfWasher(washerId).combine(
                    washerDao.getWasherFlow(washerId)
                ) { ids, washer ->
                    washerApi.get_washer_earnedMoney(token, washerId, dateFrom, dateTo)
                }.collect { earnedMoney ->
                    Log.d(TAG, "get_washer_earnedMoney: $earnedMoney")
                    emit(Resource.Success(earnedMoney))
                }
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun get_not_checked_washers(
        ids: List<Long>,
        name: String,
        page: Int,
    ): Flow<Resource<List<Washer>>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = washerApi.get_washCompany_id(token)[0]
                val washersApi = washerApi.get_washers(token, companyId, name, page).map {
                    it.toWasher()
                }
                val washerEntities = washersApi.map {
                    it.toEntity()
                }
                washerDao.insertOrUpdateWashers(
                    washerEntities
                )
                val washers = washerDao.getNotCheckedWashers(ids, name, page + 1).map {
                    it.toWasher()
                }
                Log.d(TAG, "get_not_checked_washers: $washers")
                emit(Resource.Success(washers))
            } catch (ex: Exception) {
                Log.d(TAG, "get_not_checked_washers: ${ex.message!!}")
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))
        }

}

//WasherEarnedMoney
//            val dateFromLong = getDateLong(dateFrom)
//            val dateToLong = getDateLong(dateTo)
//                    val orders = orderDao.getAllOrdersWithWashersAndServices(
//                        dateFromLong,
//                        dateToLong,
//                        false,
//                        ids
//                    )
//                    orders.sumOf { order ->
//                        order.order.price
//                    }.toLong()

//EarnedStake
//                    var sumOfStake = 0L
//                    val orders = orderDao.getAllOrdersWithWashersAndServices(
//                        dateFromLong,
//                        dateToLong,
//                        false,
//                        ids
//                    )
//                    orders.forEach { order ->
//                        var maxPercentage = 0
//                        var parts = 0
//                        order.washers.forEach { washer ->
//                            parts += washer.stake
//                            if (maxPercentage < washer.stake) {
//                                maxPercentage = washer.stake
//                            }
//                        }
//                        val priceForWashers = order.order.price * maxPercentage / 100
//                        val onePart = (100 / parts).toLong()
//                        sumOfStake += washer.stake * onePart * priceForWashers / 100
//                    }
//                    sumOfStake