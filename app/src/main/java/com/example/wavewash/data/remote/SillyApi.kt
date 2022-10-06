package com.example.wavewash.data.remote

import com.example.wavewash.data.remote.dto.LoginAnswerDto
import com.example.wavewash.data.remote.dto.LoginDto
import com.example.wavewash.data.remote.dto.WasherAnswerDto
import com.example.wavewash.data.remote.dto.WasherDto
import retrofit2.http.*


interface SillyApi {

    @POST("api/auth/login")
    suspend fun auth_login(@Body body:LoginDto): LoginAnswerDto

    //washer
    @POST("api/washer/{companyId}/add")
    suspend fun add_washer(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId:Int,
        @Body body:WasherDto
    )

    @POST("api/washer/update/{washerId}")
    suspend fun update_washer(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId:Int,
        @Body body:WasherDto
    )

    @GET("api/washer/{companyId}/getWashers")
    suspend fun get_washers(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId:Int,
        @Query("name") name:String,
        @Query("page") page:Int
    ):List<WasherAnswerDto>

    @GET("api/washer/{washerId}")
    suspend fun get_washer(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId:Int,
    ):WasherAnswerDto

    //washCompany
    @POST("api/washCompany/getId")
    suspend fun get_washCompany_id(
        @Header("Authorization") authorization: String,
    ):List<Int>

}