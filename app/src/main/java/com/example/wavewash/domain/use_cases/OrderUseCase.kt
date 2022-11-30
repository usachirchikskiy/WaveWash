package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.data.local.order.OrderDao
import com.example.wavewash.data.local.order.OrderEntity
import com.example.wavewash.data.local.order.toEntity
import com.example.wavewash.data.local.order.toOrder
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.local.service.toEntity
import com.example.wavewash.data.local.service.toService
import com.example.wavewash.data.local.washer.WasherDao
import com.example.wavewash.data.local.washer.toEntity
import com.example.wavewash.data.remote.SillyWashApi
import com.example.wavewash.data.remote.dto.order.*
import com.example.wavewash.data.remote.dto.service.toService
import com.example.wavewash.domain.model.Order
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import com.example.wavewash.utils.getDateLong
import kotlinx.coroutines.flow.*

private const val TAG = "Order"

class OrderUseCase(
    private val api: OrderApi,
    private val appDataStoreManager: AppDataStore,
    private val orderDao: OrderDao,
    private val washerDao: WasherDao,
    private val serviceDao: ServiceDao
) {

    fun addOrder(addOrderDto: AddOrderDto): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result = api.add_order(token, companyId.toLong(), addOrderDto)
                orderDao.insertOrUpdateOrder(result.toOrder().toEntity())
                result.services.forEach { service ->
                    serviceDao.insertOrUpdateService(service.toEntity())
                    if (!orderDao.isRowOrderServiceCrossRefExist(result.id, service.id)) {
                        orderDao.insertOrderServiceCrossRef(
                            OrderServiceCrossRef(result.id, service.id)
                        )
                    }
                }
                result.washers.forEach { washer ->
                    washerDao.insertOrUpdateWasher(washer.toEntity())
                    if (!orderDao.isRowOrderWasherCrossRefExist(result.id, washer.id)) {
                        orderDao.insertOrderWasherCrossRef(
                            OrderWasherCrossRef(result.id, washer.id)
                        )
                    }
                }
                Log.d(TAG, "addOrder: $result")
                emit(Resource.Success("Order Added"))
            } catch (ex: Exception) {
                Log.d(TAG, "get_washer_orders: $ex")
                emit(Resource.Error(message = ex.message!!))
            }
            emit(Resource.Loading(false))

        }

    fun update(updateOrderDto: UpdateOrderDto, orderId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.update_order(token, orderId, updateOrderDto)
                orderDao.insertOrUpdateOrder(result.toOrder().toEntity())
                result.services.forEach { service ->
                    serviceDao.insertOrUpdateService(service.toEntity())
                    if (!orderDao.isRowOrderServiceCrossRefExist(result.id, service.id)) {
                        orderDao.insertOrderServiceCrossRef(
                            OrderServiceCrossRef(result.id, service.id)
                        )
                    }
                }
                result.washers.forEach { washer ->
                    washerDao.insertOrUpdateWasher(washer.toEntity())
                    if (!orderDao.isRowOrderWasherCrossRefExist(result.id, washer.id)) {
                        orderDao.insertOrderWasherCrossRef(
                            OrderWasherCrossRef(result.id, washer.id)
                        )
                    }
                }
                emit(Resource.Success("Order Updated"))
            } catch (ex: Exception) {
                Log.d(TAG, "update Error: ${ex.message}")
                emit(Resource.Error(message = ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun delete_order(orderId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                api.delete_order(token, orderId)
                orderDao.deleteById(orderId)
                emit(Resource.Success("Order Deleted"))
            } catch (ex: Exception) {
                Log.d(TAG, "delete Error: ${ex.message}")
                emit(Resource.Error(message = ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun get_orders(
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
                val companyId = api.get_washCompany_id(token)[0]
                val result =
                    api.get_orders(token, companyId.toLong(), isActive, dateFrom, dateTo, page)
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
                Log.d(TAG, "get_orders remote: $result")
            } catch (ex: Exception) {
                emit(Resource.Error(message = ex.message!!))
            }
            orderDao.getOrdersWithWashersAndServices(
                page = page + 1,
                dateFrom = dateFromLong,
                dateTo = dateToLong,
                isActive = isActive
            ).collect { orderWithWasherAndServices ->
                Log.d(TAG, " get_orders cashed: $orderWithWasherAndServices")
                emit(Resource.Success(orderWithWasherAndServices.map { it.toOrder() }))
                emit(Resource.Loading<List<Order>>(false))
            }
//            Log.d(TAG, "get_orders: LOADING FALSE")
//            emit(Resource.Loading<List<Order>>(false))
        }

    fun get_order(orderId: Long): Flow<Resource<Order>> =
        flow {
            emit(Resource.Loading())
            try {
                val order = orderDao.getOrderByIdWithWashersAndServices(orderId).toOrder()
                emit(Resource.Success(order))
            } catch (ex: Exception) {
                emit(Resource.Error(message = "Order is null"))
            }
            emit(Resource.Loading(false))
        }

}
