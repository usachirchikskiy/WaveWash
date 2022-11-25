package com.example.wavewash.domain.validation_use_case

import com.example.wavewash.R

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