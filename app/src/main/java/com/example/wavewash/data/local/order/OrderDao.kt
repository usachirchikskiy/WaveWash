package com.example.wavewash.data.local.order

import androidx.room.*
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.data.local.service.ServiceEntity
import com.example.wavewash.utils.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    //Order
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    suspend fun insertOrUpdateOrder(orderEntity: OrderEntity){
        val orderEntityDb = getOrderById(orderEntity.orderId)
        if(orderEntityDb!=orderEntity){
            insertOrder(orderEntity)
        }
    }

    @Query("DELETE FROM orders where orderId = :orderId")
    suspend fun deleteById(orderId: Long)

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: Long):OrderEntity

    //OrderWasherCrossRef
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderWasherCrossRef(crossRef: OrderWasherCrossRef)

    @Query("SELECT EXISTS(SELECT * FROM OrderWasherCrossRef WHERE orderId = :orderId and washerId =:washerId)")
    suspend fun isRowOrderWasherCrossRefExist(orderId : Long,washerId: Long) : Boolean

    //OrderServiceCrossRef
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderServiceCrossRef(crossRef: OrderServiceCrossRef)

    @Query("SELECT EXISTS(SELECT * FROM OrderServiceCrossRef WHERE orderId = :orderId and serviceId =:serviceId)")
    suspend fun isRowOrderServiceCrossRefExist(orderId : Long,serviceId: Long) : Boolean

    //OrderDetail
    @Transaction
    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderByIdWithWashersAndServices(orderId: Long): OrderWithWashersAndServices

    //Pagination OrdersWithWashersAndServices for OrderInfo
    @Transaction
    @Query("""
        SELECT * FROM orders
        WHERE active = :isActive AND
        date BETWEEN :dateFrom AND :dateTo
        ORDER BY orderId DESC
        LIMIT (:page * :pageSize)
        """)
    fun getOrdersWithWashersAndServices(
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE,
        dateFrom:Long,
        dateTo:Long,
        isActive:Boolean
    ):Flow<List<OrderWithWashersAndServices>>

//    AllOrdersWithServicesAndWashers for Analytics
//    @Transaction
//    @Query("""
//        SELECT * FROM orders
//        WHERE orderId in (:ids) AND
//        active = :isActive AND
//        date BETWEEN :dateFrom AND :dateTo
//        """)
//    suspend fun getAllOrdersWithWashersAndServices(
//        dateFrom:Long,
//        dateTo:Long,
//        isActive:Boolean,
//        ids:List<Long>
//    ):List<OrderWithWashersAndServices>

    //Pagination OrderWithServices
    @Transaction
    @Query(
        """
        SELECT * FROM orders
        WHERE orderId in (:ids) AND
        active = :isActive AND
        date BETWEEN :dateFrom AND :dateTo
        ORDER BY orderId DESC
        LIMIT (:page * :pageSize)
        """
    )
    suspend fun getOrdersWithServices(
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE,
        dateFrom:Long,
        dateTo:Long,
        isActive:Boolean,
        ids:List<Long>
    ):List<OrderWithServices>

    //Pagination ordersOfWasher
    @Query(
        """
            SELECT orderId FROM OrderWasherCrossRef 
            WHERE washerId = :washerId
            ORDER BY orderId DESC
            LIMIT (:page * :pageSize)
        """
    )
    fun getOrdersOfWasher(
        washerId: Long,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE
    ): Flow<List<Long>>


}