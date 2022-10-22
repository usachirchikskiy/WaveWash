package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.data.remote.dto.order.OrderAddDto
import com.example.wavewash.data.remote.dto.order.OrderAnswerDto
import com.example.wavewash.data.remote.dto.order.OrderUpdateDto
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

private const val TAG = "Order"

class Order(
    val api: SillyApi,
    val appDataStoreManager: AppDataStore
) {

    fun addOrder(orderAddDto: OrderAddDto): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result = api.add_order(token, companyId.toLong(), orderAddDto)
                Log.d(TAG, "Result: addOrder $result")
            } catch (ex: Exception) {
                throw ex
            }
            emit(Resource.Success("Order added"))

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun update(orderUpdateDto: OrderUpdateDto, orderId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.update_order(token, orderId, orderUpdateDto)
                Log.d(TAG, "Result: $result")
            } catch (ex: Exception) {
                throw ex
            }
            emit(Resource.Success("Order updated"))
        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_orders(
        isActive: Boolean,
        dateFrom: String,
        dateTo: String,
        page: Int
    ): Flow<Resource<List<OrderAnswerDto>>> =
        flow<Resource<List<OrderAnswerDto>>> {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result =
                    api.get_orders(token, companyId.toLong(), isActive, dateFrom, dateTo, page)

                emit(Resource.Success(result))


            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_order(orderId: Long): Flow<Resource<OrderAnswerDto>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.get_order(token, orderId)
                emit(Resource.Success(result))
                Log.d(TAG, "Result: getOrder  $result")
            } catch (ex: Exception) {
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }
}