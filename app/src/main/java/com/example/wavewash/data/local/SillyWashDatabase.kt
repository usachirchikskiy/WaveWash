package com.example.wavewash.data.local

import androidx.room.Database
import androidx.room.DatabaseView
import androidx.room.RoomDatabase
import com.example.wavewash.data.local.crossRef.OrderServiceCrossRef
import com.example.wavewash.data.local.crossRef.OrderWasherCrossRef
import com.example.wavewash.data.local.order.OrderDao
import com.example.wavewash.data.local.order.OrderEntity
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.local.service.ServiceEntity
import com.example.wavewash.data.local.washer.WasherDao
import com.example.wavewash.data.local.washer.WasherEntity

@Database(
    entities = [OrderEntity::class,ServiceEntity::class,WasherEntity::class,OrderServiceCrossRef::class,OrderWasherCrossRef::class],
    version = 1
)
abstract class SillyWashDatabase: RoomDatabase() {
    abstract val serviceDao: ServiceDao
    abstract val washerDao: WasherDao
    abstract val orderDao: OrderDao
}