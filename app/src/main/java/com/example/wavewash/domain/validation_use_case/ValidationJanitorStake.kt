package com.example.wavewash.domain.validation_use_case

import com.example.wavewash.R

class ValidationJanitorStake {
    fun execute(stake: String): ValidationResult {
        if(stake.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.required_field
            )
        }
        else if(stake.contains(".") || stake.contains(",")){
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