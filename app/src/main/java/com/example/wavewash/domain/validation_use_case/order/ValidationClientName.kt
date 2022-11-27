package com.example.wavewash.domain.validation_use_case.order

import com.example.wavewash.R
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationClientName {
    fun execute(clientName: String): ValidationResult {
        if (clientName.isEmpty()) {
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