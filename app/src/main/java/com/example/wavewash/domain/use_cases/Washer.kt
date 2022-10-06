package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.data.remote.dto.WasherAnswerDto
import com.example.wavewash.data.remote.dto.WasherDto
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

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

    fun update(washerDto: WasherDto, washerId: Int): Flow<Resource<String>> =
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
//                Log.d(TAG, "get_washers: $token")
                val companyId = api.get_washCompany_id(token)[0]
//                Log.d(TAG, "Token: $token id: $companyId name: $name page: $page")
                val result = api.get_washers(token,companyId,name,page)
                Log.d(TAG, "Result: getWasher  $result")
                emit(Resource.Success(result))
            }catch(ex:Exception){
                throw ex
            }

        }.catch { e->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }

    fun get_washer(washerId:Int):Flow<Resource<WasherAnswerDto>> =
        flow{
            emit(Resource.Loading())
            try {
                val token = "Bearer " + appDataStoreManager.readValue(TOKEN_KEY)
                val result = api.get_washer(token,washerId)
                emit(Resource.Success(result))
            }catch(ex:Exception){
                throw ex
            }

        }.catch { e->
            Log.d(TAG, "Exception: $e")
            emit(Resource.Error(e.message!!))
        }
}