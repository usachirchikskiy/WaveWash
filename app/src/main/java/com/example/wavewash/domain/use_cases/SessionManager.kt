package com.example.wavewash.domain.use_cases

import android.util.Log
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyWashApi
import com.example.wavewash.data.remote.dto.login.LoginDto
import com.example.wavewash.utils.*

private const val TAG = "SessionManager"
class SessionManager(
    private val api: SillyWashApi,
    private val appDataStoreManager: AppDataStore
) {
    suspend fun execute(): Resource<String> {
        try {
            val password = appDataStoreManager.readValue(PASSWORD_KEY)
            val email = appDataStoreManager.readValue(EMAIL_KEY)
            if (password!=null && email!=null) {
                val body = LoginDto(email, password)
                val result = api.auth_login(body)
                appDataStoreManager.setValue(TOKEN_KEY, result.accessToken)
                appDataStoreManager.setValue(EMAIL_KEY, body.email)
                appDataStoreManager.setValue(PASSWORD_KEY, body.password)
                Log.d(TAG, "Result: $result")
                return Resource.Success(data = SUCCESS_LOGIN)
            }
            return Resource.Error(message = "Not Logged In")
        } catch (ex: Exception) {
            return Resource.Error(message = ex.message!!)
        }
    }
}