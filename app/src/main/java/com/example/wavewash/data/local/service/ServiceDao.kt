package com.example.wavewash.data.local.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wavewash.utils.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(
        serviceEntity: ServiceEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(
        servicesEntity: List<ServiceEntity>
    )

    @Query("SELECT * FROM service WHERE serviceId = :id")
    suspend fun getService(id: Long): ServiceEntity?


    @Query("""
        SELECT * FROM service 
        WHERE name LIKE '%' || :query || '%'
        ORDER BY name ASC
        LIMIT (:page * :pageSize)
        """)
    fun getAllServices(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE
    ): Flow<List<ServiceEntity>>
}