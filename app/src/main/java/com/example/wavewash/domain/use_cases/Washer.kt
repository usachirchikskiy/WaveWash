package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.data.remote.dto.order.OrderAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherDto
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import retrofit2.http.Path
import retrofit2.http.Query

private const val TAG = "Washer"

class Washer(
    val api: SillyApi,
    val appDataStoreManager: AppDataStore
) {
    fun addWasher(washerDto: WasherDto): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result = api.add_washer(token, companyId, washerDto)
                Log.d(TAG, "Result: addWasher $result")
            } catch (ex: Exception) {
                throw ex
            }
            emit(Resource.Success("Washer added"))
        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun update(washerDto: WasherDto, washerId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.update_washer(token, washerId, washerDto)
                Log.d(TAG, "Result: $result")
            } catch (ex: Exception) {
                throw ex
            }
            emit(Resource.Success("Washer updated"))
        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_washers(name: String, page: Int): Flow<Resource<List<WasherAnswerDto>>> =
        flow<Resource<List<WasherAnswerDto>>> {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result = api.get_washers(token, companyId, name, page)
                Log.d(TAG, "Result: getWasher  $result")
                emit(Resource.Success(result))
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_washer(washerId: Long): Flow<Resource<WasherAnswerDto>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.get_washer(token, washerId)
                emit(Resource.Success(result))
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_washer_orders(
        washerId: Long,
        isActive: Boolean,
        dateFrom: String,
        dateTo: String,
        page: Int
    ): Flow<Resource<List<OrderAnswerDto>>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result =
                    api.get_washer_orders(token, washerId, isActive, dateFrom, dateTo, page)
                emit(Resource.Success(result))
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
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
                val result = api.get_washer_earnedMoney(token, washerId, dateFrom, dateTo)
                emit(Resource.Success(result))
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
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
                val result = api.get_washer_earnedStake(token, washerId, dateFrom, dateTo)
                emit(Resource.Success(result))
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_free_washers(
        companyId: Long,
        name:String,
        page:Int,
    ): Flow<Resource<List<OrderAnswerDto>>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.get_free_washers(token, companyId, name, page)
                emit(Resource.Success(result))
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }
}
