package com.example.wavewash.data.remote.dto.order

import retrofit2.http.*

interface OrderApi{
    @POST("api/order/{companyId}/addOrder")
    suspend fun add_order(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Long,
        @Body body: AddOrderDto
    ):OrderDto

    @POST("api/order/updateOrder/{orderId}")
    suspend fun update_order(
        @Header("Authorization") authorization: String,
        @Path("orderId") orderId: Long,
        @Body body: UpdateOrderDto
    ):OrderDto

    @GET("api/order/{companyId}/getOrders")
    suspend fun get_orders(
        @Header("Authorization") authorization: String,
        @Path("companyId") companyId: Long,
        @Query("isActive") isActive:Boolean,
        @Query("dateFrom") dateFrom:String,
        @Query("dateTo") dateTo:String,
        @Query("page") page:Int,
    ):List<OrderDto>

    @GET("api/order/{id}")
    suspend fun get_order(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
    ): OrderDto

    @POST("api/washCompany/getId")
    suspend fun get_washCompany_id(
        @Header("Authorization") authorization: String,
    ): List<Int>

    @POST("/api/order/deleteOrder/{orderId}")
    suspend fun delete_order(
        @Header("Authorization") authorization: String,
        @Path("orderId") orderId: Long,
    )

}