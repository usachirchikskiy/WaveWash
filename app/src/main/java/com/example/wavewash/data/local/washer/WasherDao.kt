package com.example.wavewash.data.local.washer

import androidx.room.*
import com.example.wavewash.data.local.order.OrderWithWashersAndServices
import com.example.wavewash.data.local.service.ServiceEntity
import com.example.wavewash.utils.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface WasherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWasher(
        washerEntity: WasherEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWashers(
        washerEntity: List<WasherEntity>
    )

    suspend fun insertOrUpdateWasher(washerEntity: WasherEntity){
        val washerEntityDb = getWasherNotFlow(washerEntity.washerId)
        if(washerEntityDb!=washerEntity){
            insertWasher(washerEntity)
        }
    }

    suspend fun insertOrUpdateWashers(washersEntity: List<WasherEntity>){
        washersEntity.forEach {washerEntity->
            val washerEntityDb = getWasherNotFlow(washerEntity.washerId)
            if(washerEntityDb!=washerEntity){
                insertWasher(washerEntity)
            }
        }
    }

    @Query("UPDATE washer SET deleted = :deleted WHERE washerId = :id")
    suspend fun deleteById(id: Long, deleted:Boolean = true)

    @Query("SELECT * FROM washer WHERE washerId = :id")
    suspend fun getWasherNotFlow(id: Long): WasherEntity

    @Query("SELECT * FROM washer WHERE washerId = :id")
    fun getWasherFlow(id: Long): Flow<WasherEntity>

    @Query(
        """
        SELECT * FROM washer 
        WHERE name LIKE '%' || :query || '%'
        AND deleted = :deleted
        ORDER BY name ASC
        LIMIT (:page * :pageSize)
        """
    )
    fun getAllWashers(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE,
        deleted: Boolean = false
    ): Flow<List<WasherEntity>>

    @Query(
        """
        SELECT * FROM washer 
        WHERE name LIKE '%' || :query || '%'
        AND deleted = :deleted
        AND washerId not in (:ids)  
        ORDER BY name ASC
        LIMIT (:page * :pageSize)
        """
    )
    suspend fun getNotCheckedWashers(
        ids:List<Long>,
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE,
        deleted: Boolean = false
    ): List<WasherEntity>

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

    @Query("SELECT orderId FROM OrderWasherCrossRef WHERE washerId = :washerId")
    fun getAllOrdersOfWasher(
        washerId: Long
    ): Flow<List<Long>>

}