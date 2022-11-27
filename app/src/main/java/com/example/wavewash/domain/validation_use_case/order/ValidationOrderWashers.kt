package com.example.wavewash.domain.validation_use_case.order

import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationOrderWashers {
    fun execute(washers: List<Washer>): ValidationResult {
        if(washers.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.choose_janitor
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}