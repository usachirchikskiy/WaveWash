package com.example.wavewash.domain.model

data class Washer(
    val id:Long,
    val name:String,
    val telephoneNumber:String,
    val active:Boolean,
    val image:String,
    val stake:Int,
    val deleted:Boolean
)
