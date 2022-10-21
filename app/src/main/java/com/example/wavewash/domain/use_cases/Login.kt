package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.data.remote.dto.login.LoginDto
import com.example.wavewash.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

private const val TAG = ""

class Login(
    val api:SillyApi,
    val appDataStoreManager: AppDataStore
) {
    fun execute(body: LoginDto) : Flow<Resource<String>> = flow<Resource<String>> {
        emit(Resource.Loading())
        try {
            val result = api.auth_login(body)
            appDataStoreManager.setValue(TOKEN_KEY,result.accessToken)
            appDataStoreManager.setValue(EMAIL_KEY,body.email)
            appDataStoreManager.setValue(PASSWORD_KEY,body.password)
            Log.d(TAG, "Result: $result")
        } catch (ex: Exception){
            throw ex
        }
        emit(Resource.Success(data = SUCCESS_LOGIN))

    }.catch { e->
        emit(Resource.Error(message = e.message!!))
        Log.d(TAG, "Exception: $e")
    }
}