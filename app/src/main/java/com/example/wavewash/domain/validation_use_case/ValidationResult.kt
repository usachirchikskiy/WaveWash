package com.example.wavewash.domain.validation_use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: Int? = null
)