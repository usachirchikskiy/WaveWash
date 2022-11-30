package com.example.wavewash.data.local.service

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wavewash.domain.model.Service

@Entity(tableName = "service")
data class ServiceEntity(
    @PrimaryKey(autoGenerate = false)
    val serviceId:Long,
    val name:String,
    val duration: Int,
    val price:Int,
    val deleted:Boolean
)

fun ServiceEntity.toService(): Service{
    return Service(
        id = serviceId,
        name = name,
        duration = duration,
        price = price,
        deleted = deleted
    )
}

fun Service.toEntity(): ServiceEntity {
    return ServiceEntity(
        serviceId = id,
        name = name,
        duration = duration,
        price = price,
        deleted = deleted
    )
}
