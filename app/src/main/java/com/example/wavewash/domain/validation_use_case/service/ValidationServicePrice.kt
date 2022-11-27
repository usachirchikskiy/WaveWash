package com.example.wavewash.domain.validation_use_case.service

import com.example.wavewash.R
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationServicePrice {
    fun execute(price: String): ValidationResult {
        if(price.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.required_field
            )
        }
        else if(price.contains(".") || price.contains(",")){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.comma_dot_field
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}