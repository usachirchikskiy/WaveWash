package com.example.wavewash.domain.validation_use_case.order

import com.example.wavewash.R
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationClientTelephoneNumber {
    fun execute(telephoneNumber: String): ValidationResult {
        if(telephoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.required_field
            )
        }
        else if(telephoneNumber.contains(".") || telephoneNumber.contains(",")){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.comma_dot_field
            )
        }
        else if(telephoneNumber.length!=9){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.telephone_number_size
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}