package com.example.wavewash.data.remote

import com.example.wavewash.data.remote.dto.*
import com.example.wavewash.data.remote.dto.login.LoginAnswerDto
import com.example.wavewash.data.remote.dto.login.LoginDto
import com.example.wavewash.data.remote.dto.order.OrderAddDto
import com.example.wavewash.data.remote.dto.order.OrderAnswerDto
import com.example.wavewash.data.remote.dto.order.OrderUpdateDto
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherDto
import retrofit2.http.*


interface SillyApi {
    //login
    @POST("api/auth/login")
    suspend fun auth_login(@Body body: LoginDto): LoginAnswerDto

    //washer
    @POST("api/washer/{companyId}/add")
    suspend fun add_washer(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Body body: WasherDto
    )

    @POST("api/washer/update/{washerId}")
    suspend fun update_washer(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Body body: WasherDto
    )

    @GET("api/washer/{companyId}/getWashers")
    suspend fun get_washers(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Query("name") name: String,
        @Query("page") page: Int
    ): List<WasherAnswerDto>

    @GET("api/washer/{washerId}")
    suspend fun get_washer(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
    ): WasherAnswerDto

    @GET("api/washer/{washerId}/getOrders")
    suspend fun get_washer_orders(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Query("isActive") isActive:Boolean,
        @Query("dateFrom") dateFrom:String,
        @Query("dateTo") dateTo:String,
        @Query("page") page:Int,
    ):List<OrderAnswerDto>

    @GET("api/washer/{washerId}/earnedStake")
    suspend fun get_washer_earnedStake(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Query("dateFrom") dateFrom:String,
        @Query("dateTo") dateTo:String,
    ):Long

    @GET("api/washer/{washerId}/earnedMoney")
    suspend fun get_washer_earnedMoney(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Query("dateFrom") dateFrom:String,
        @Query("dateTo") dateTo:String,
    ):Long

    @GET("api/washer/{companyId}/getFreeWashers")
    suspend fun get_free_washers(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Long,
        @Query("name") name:String,
        @Query("page") page:Int,
    ):List<OrderAnswerDto>

    //services
    @POST("api/service/{companyId}/add")
    suspend fun add_service(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Body body: ServiceDto
    )

    @POST("api/service/{id}/update")
    suspend fun update_service(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
        @Body body: ServiceDto
    )

    @GET("api/service/{companyId}/getServices")
    suspend fun get_services(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Query("searchName") name: String,
        @Query("page") page: Int
    ): List<ServiceAnswerDto>

    @GET("api/service/{id}")
    suspend fun get_service(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
    ): ServiceAnswerDto

    //order
    @POST("api/order/{companyId}/addOrder")
    suspend fun add_order(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Long,
        @Body body: OrderAddDto
    )

    @POST("api/order/updateOrder/{orderId}")
    suspend fun update_order(
        @Header("Authorization") authorization: String,
        @Path("orderId") orderId: Long,
        @Body body: OrderUpdateDto
    )

    @GET("api/order/{companyId}/getOrders")
    suspend fun get_orders(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Long,
        @Query("isActive") isActive:Boolean,
        @Query("dateFrom") dateFrom:String,
        @Query("dateTo") dateTo:String,
        @Query("page") page:Int,
    ):List<OrderAnswerDto>

    @GET("api/order/{id}")
    suspend fun get_order(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
    ): OrderAnswerDto

    //washCompany
    @POST("api/washCompany/getId")
    suspend fun get_washCompany_id(
        @Header("Authorization") authorization: String,
    ): List<Int>

}