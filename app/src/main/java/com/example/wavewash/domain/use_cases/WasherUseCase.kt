package com.example.wavewash.domain.use_cases

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.washer.WasherDao
import com.example.wavewash.data.local.washer.WasherEntity
import com.example.wavewash.data.local.washer.toEntity
import com.example.wavewash.data.local.washer.toWasher
import com.example.wavewash.data.remote.dto.order.OrderDto
import com.example.wavewash.data.remote.dto.order.toOrder
import com.example.wavewash.data.remote.dto.washer.WasherApi
import com.example.wavewash.data.remote.dto.washer.toWasher
import com.example.wavewash.domain.model.Order
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody

private const val TAG = "Washer"

class WasherUseCase(
    val washerApi: WasherApi,
    val appDataStoreManager: AppDataStore,
    val washerDao: WasherDao
) {

    fun addWasher(data: MultipartBody.Part, image: MultipartBody.Part?): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = washerApi.get_washCompany_id(token)[0]
                val washer = washerApi.add_washer(token, companyId, data, image).toWasher()
                washerDao.insertWasher(washer.toEntity())
                emit(Resource.Success("washer added"))
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
                washerDao.insertWasher(washer.toEntity())
                Log.d(TAG, "update: $washer")
                emit(Resource.Success("update"))
            } catch (ex: Exception) {
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
                washerDao.insertWashers(
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
                Log.d(TAG, "get_washers page = $page $name")
                Log.d(TAG, "get_washers cached: $washers")
                emit(Resource.Success(washers))
            }

            emit(Resource.Loading(false))
        }

    fun get_washer(washerId: Long): Flow<Resource<Washer>> =
        flow {
            emit(Resource.Loading())
            try {
                washerDao.getWasher(washerId).collect{

                    Log.d(TAG, "get_washer: cached")
                    emit(Resource.Success(it.toWasher()))
                }
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
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result =
                    washerApi.get_washer_orders(token, washerId, isActive, dateFrom, dateTo, page)
                emit(Resource.Success(result.map { it.toOrder() }))
                Log.d(TAG, "get_washer_orders: $result")
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
                val result = washerApi.get_washer_earnedMoney(token, washerId, dateFrom, dateTo)
                emit(Resource.Success(result))
                Log.d(TAG, "get_washer_earnedMoney: $result")
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
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
                val result = washerApi.get_washer_earnedStake(token, washerId, dateFrom, dateTo)
                emit(Resource.Success(result))
                Log.d(TAG, "get_washer_earnedStake: $result")
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun get_free_washers(
        companyId: Long,
        name: String,
        page: Int,
    ): Flow<Resource<List<OrderDto>>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = washerApi.get_free_washers(token, companyId, name, page)
                emit(Resource.Success(result))
                Log.d(TAG, "get_free_washers: $result")
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))
        }
}
