package com.example.wavewash.domain.model

data class Order(
    val workerImageUrl:String,
    val workerName:String,
    val carImageUrl:String,
    val carName:String,
    val carNumber:String,
    val quantityOfServices:Int,
    val time:String
)