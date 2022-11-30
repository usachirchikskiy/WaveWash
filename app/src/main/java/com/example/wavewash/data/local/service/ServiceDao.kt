package com.example.wavewash.data.local.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wavewash.data.local.washer.WasherEntity
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

    suspend fun insertOrUpdateService(serviceEntity: ServiceEntity){
        val serviceEntityDb = getService(serviceEntity.serviceId)
        if(serviceEntityDb!=serviceEntity){
            insertService(serviceEntity)
        }
    }

    suspend fun insertOrUpdateServices(servicesEntity: List<ServiceEntity>){
        servicesEntity.forEach {serviceEntity->
            val serviceEntityDb = getService(serviceEntity.serviceId)
            if(serviceEntityDb!=serviceEntity){
                insertService(serviceEntity)
            }
        }
    }

    @Query("SELECT * FROM service WHERE serviceId = :id")
    suspend fun getService(id: Long): ServiceEntity?


    @Query("""
        SELECT * FROM service 
        WHERE name LIKE '%' || :query || '%'
        AND deleted = :deleted
        ORDER BY name ASC
        LIMIT (:page * :pageSize)
        """)
    fun getAllServices(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE,
        deleted: Boolean = false
    ): Flow<List<ServiceEntity>>

    @Query("UPDATE service SET deleted = :deleted WHERE serviceId = :id")
    suspend fun deleteById(id: Long, deleted:Boolean = true)

    @Query(
        """
        SELECT * FROM service 
        WHERE name LIKE '%' || :query || '%'
        AND deleted = :deleted
        AND serviceId not in (:ids)  
        ORDER BY name ASC
        LIMIT (:page * :pageSize)
        """
    )
    suspend fun getNotCheckedServices(
        ids:List<Long>,
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE,
        deleted: Boolean = false
    ): List<ServiceEntity>

}