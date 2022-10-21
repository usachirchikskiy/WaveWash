package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

private const val TAG = "Service"
class Service(
    val api: SillyApi,
    val appDataStoreManager: AppDataStore
) {
    fun addService(serviceDto: ServiceDto): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result = api.add_service(token, companyId, serviceDto)
                Log.d(TAG, "Result: addService $result")
            } catch (ex: Exception) {
                throw ex
            }
            emit(Resource.Success("Service added"))
        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun update(serviceDto: ServiceDto, serviceId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.update_service(token, serviceId, serviceDto)
                Log.d(TAG, "Result: $result")
            } catch (ex: Exception) {
                throw ex
            }
            emit(Resource.Success("Service updated"))
        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_services(name: String, page: Int): Flow<Resource<List<ServiceAnswerDto>>> =
        flow<Resource<List<ServiceAnswerDto>>> {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = api.get_washCompany_id(token)[0]
                val result = api.get_services(token,companyId,name,page)
                Log.d(TAG, "Result: getServices  $result")
                emit(Resource.Success(result))
            }catch(ex:Exception){
                throw ex
            }

        }.catch { e->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_service(serviceId:Long): Flow<Resource<ServiceAnswerDto>> =
        flow{
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.get_service(token,serviceId)
                emit(Resource.Success(result))
                Log.d(TAG, "Result: getService  $result")
            }catch(ex:Exception){
                throw ex
            }

        }.catch { e ->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

}