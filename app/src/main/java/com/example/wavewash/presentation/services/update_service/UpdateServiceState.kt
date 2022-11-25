package com.example.wavewash.presentation.services.update_service

data class UpdateServiceState(
    val id: Long = -1,
    val name: String = "",
    val price: String = "",
    val duration: String = "",
    val isLoading: Boolean = false,
    val error: String = "",

    val nameError: Int? = null,
    val priceError: Int? = null,
    val durationError: Int? = null
)