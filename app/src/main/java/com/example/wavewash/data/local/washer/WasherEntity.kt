package com.example.wavewash.data.local.washer

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wavewash.data.local.service.ServiceEntity
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer

@Entity(tableName = "washer")
data class WasherEntity(
    @PrimaryKey(autoGenerate = false)
    val washerId:Long,
    val name:String,
    val telephoneNumber:String,
    val active:Boolean,
    val image:String,
    val stake:Int,
    val deleted:Boolean
)

fun WasherEntity.toWasher(): Washer {
    return Washer(
        id = washerId,
        name = name,
        telephoneNumber = telephoneNumber,
        active = active,
        image = image,
        stake = stake,
        deleted = deleted
    )
}

fun Washer.toEntity(): WasherEntity {
    return WasherEntity(
        washerId = id,
        name = name,
        telephoneNumber = telephoneNumber,
        active = active,
        image = image,
        stake = stake,
        deleted = deleted
    )
}