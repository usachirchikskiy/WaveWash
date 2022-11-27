package com.example.wavewash.domain.validation_use_case.order

import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationOrderCarNumber {
    fun execute(carNumber: String): ValidationResult {
        if(carNumber.isEmpty()) {
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