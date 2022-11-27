package com.example.wavewash.domain.validation_use_case.service

import com.example.wavewash.R
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationServiceTime {
    fun execute(time: String): ValidationResult {
        if(time.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.required_field
            )
        }
        else if(time.contains(".") || time.contains(",")){
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