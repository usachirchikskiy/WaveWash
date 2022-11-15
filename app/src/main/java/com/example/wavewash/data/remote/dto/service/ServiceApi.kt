package com.example.wavewash.data.remote.dto.service

import retrofit2.http.*

interface ServiceApi {

    @POST("api/service/{companyId}/add")
    suspend fun add_service(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Body body: AddServiceDto
    ):ServiceDto

    @POST("api/service/{id}/update")
    suspend fun update_service(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
        @Body body: AddServiceDto
    ):ServiceDto

    @GET("api/service/{companyId}/getServices")
    suspend fun get_services(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Int,
        @Query("searchName") name: String,
        @Query("page") page: Int
    ): List<ServiceDto>

    @GET("api/service/{id}")
    suspend fun get_service(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
    ): ServiceDto

    @POST("api/washCompany/getId")
    suspend fun get_washCompany_id(
        @Header("Authorization") authorization: String,
    ): List<Int>
}