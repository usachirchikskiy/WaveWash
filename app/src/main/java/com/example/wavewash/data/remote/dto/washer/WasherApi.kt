package com.example.wavewash.data.remote.dto.washer

import com.example.wavewash.data.remote.dto.order.OrderDto
import okhttp3.MultipartBody
import retrofit2.http.*

interface WasherApi {
    
    @Multipart
    @POST("api/washer/{companyId}/add")
    suspend fun add_washer(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Part data: MultipartBody.Part,
        @Part file: MultipartBody.Part?
    ): WasherDto

    @Multipart
    @POST("api/washer/update/{washerId}")
    suspend fun update_washer(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Part data: MultipartBody.Part,
        @Part file: MultipartBody.Part?
    ): WasherDto

    @GET("api/washer/{companyId}/getWashers")
    suspend fun get_washers(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Query("name") name: String,
        @Query("page") page: Int
    ): List<WasherDto>

    @GET("api/washer/{washerId}")
    suspend fun get_washer(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
    ): WasherDto

    @GET("api/washer/{washerId}/getOrders")
    suspend fun get_washer_orders(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Query("isActive") isActive: Boolean,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
        @Query("page") page: Int,
    ): List<OrderDto>

    @GET("api/washer/{washerId}/earnedStake")
    suspend fun get_washer_earnedStake(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
    ): Long

    @GET("api/washer/{washerId}/earnedMoney")
    suspend fun get_washer_earnedMoney(
        @Header("Authorization") authorization: String,
        @Path("washerId") washerId: Long,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
    ): Long

    @GET("api/washer/{companyId}/getFreeWashers")
    suspend fun get_free_washers(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Long,
        @Query("name") name: String,
        @Query("page") page: Int,
    ): List<OrderDto>

    @POST("api/washCompany/getId")
    suspend fun get_washCompany_id(
        @Header("Authorization") authorization: String,
    ): List<Int>
}