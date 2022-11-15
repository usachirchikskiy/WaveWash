package com.example.wavewash.data.remote

import com.example.wavewash.data.remote.dto.*
import com.example.wavewash.data.remote.dto.login.LoginAnswerDto
import com.example.wavewash.data.remote.dto.login.LoginDto
import com.example.wavewash.data.remote.dto.order.AddOrderDto
import com.example.wavewash.data.remote.dto.order.OrderDto
import com.example.wavewash.data.remote.dto.order.UpdateOrderDto
import retrofit2.http.*


interface  SillyWashApi {
    //login
    @POST("api/auth/login")
    suspend fun auth_login(@Body body: LoginDto): LoginAnswerDto




    //washCompany
    @POST("api/washCompany/getId")
    suspend fun get_washCompany_id(
        @Header("Authorization") authorization: String,
    ): List<Int>

}