package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.local.service.toEntity
import com.example.wavewash.data.local.service.toService
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.data.remote.dto.service.AddServiceDto
import com.example.wavewash.data.remote.dto.service.ServiceApi
import com.example.wavewash.data.remote.dto.service.toService
import com.example.wavewash.domain.model.Service
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import kotlinx.coroutines.flow.*

private const val TAG = "Service"

class ServiceUseCase(
    val serviceApi: ServiceApi,
    val appDataStoreManager: AppDataStore,
    val serviceDao: ServiceDao
) {

    fun addService(addServiceDto: AddServiceDto): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = serviceApi.get_washCompany_id(token)[0]
                val service = serviceApi.add_service(token, companyId, addServiceDto).toService()
                serviceDao.insertService(service.toEntity())
                Log.d(TAG, "Service added: $service")
                emit(Resource.Success("service added"))
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun update(addServiceDto: AddServiceDto, serviceId: Long): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val service = serviceApi.update_service(token, serviceId, addServiceDto).toService()
                serviceDao.insertService(service.toEntity())
                Log.d(TAG, "Service updated: $service")
                emit(Resource.Success("service updated"))
            } catch (ex: Exception) {
                emit(Resource.Error(message = ex.message!!))
            }
            emit(Resource.Loading(false))
        }

    fun get_services(name: String, page: Int): Flow<Resource<List<Service>>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val companyId = serviceApi.get_washCompany_id(token)[0]
                val services = serviceApi.get_services(token, companyId, name, page).map {
                    it.toService()
                }
                serviceDao.insertServices(
                    services.map {
                        it.toEntity()
                    }
                )
            } catch (ex: Exception) {
                emit(Resource.Error(ex.message!!))
            }

            serviceDao.getAllServices(query = name, page = page + 1).collect {
                val services = it.map { entity ->
                    entity.toService()
                }
                Log.d(TAG, "get_services cached: $services")
                emit(Resource.Success(services))
            }
            emit(Resource.Loading(false))
        }

    fun get_service(serviceId: Long): Flow<Resource<Service>> =
        flow {
            emit(Resource.Loading())
            val service = serviceDao.getService(serviceId)?.toService()
            if(service != null){
                emit(Resource.Success(service))
            }
            else{
                emit(Resource.Error(message = "Service is null"))
            }
            emit(Resource.Loading(false))
        }

}