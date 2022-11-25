package com.example.wavewash.data.local.order

import androidx.room.*
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.utils.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderWasherCrossRef(crossRef: OrderWasherCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderServiceCrossRef(crossRef: OrderServiceCrossRef)

    @Transaction
    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    fun getOrderByIdWithWashersAndServices(orderId: Long): Flow<OrderWithWashersAndServices>

    //Pagination OrdersWithWashersAndServices
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

    //AllOrdersWithServicesAndWashers
    @Transaction
    @Query("""
        SELECT * FROM orders
        WHERE orderId in (:ids) AND 
        active = :isActive AND
        date BETWEEN :dateFrom AND :dateTo
        """)
    suspend fun getAllOrdersWithWashersAndServices(
        dateFrom:Long,
        dateTo:Long,
        isActive:Boolean,
        ids:List<Long>
    ):List<OrderWithWashersAndServices>

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