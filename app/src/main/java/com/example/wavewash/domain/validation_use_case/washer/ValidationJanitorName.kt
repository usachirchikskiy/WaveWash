package com.example.wavewash.domain.validation_use_case.washer

import com.example.wavewash.R
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationJanitorName {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.required_field
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}