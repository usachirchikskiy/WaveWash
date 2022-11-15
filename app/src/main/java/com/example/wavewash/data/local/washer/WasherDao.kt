package com.example.wavewash.data.local.washer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM washer WHERE washerId = :id")
    fun getWasher(id: Long): Flow<WasherEntity>

    @Query("""
        SELECT * FROM washer 
        WHERE name LIKE '%' || :query || '%'
        ORDER BY name ASC
        LIMIT (:page * :pageSize)
        """)
    fun getAllWashers(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE
    ): Flow<List<WasherEntity>>
}