package com.puj.stepsfitnessgame.presentation

class InputValidator(val validatedValue: String) {

    private var isValid = true
    private val errorMessages = ArrayList<String>()

    fun validate(): ValidationResult {
        return ValidationResult(isValid, errorMessages)
    }

    fun isEmail(): InputValidator {
        return this
    }

    fun hasUpperCase(): InputValidator {
        return this
    }

    fun hasLowerCase(): InputValidator {
        return this
    }

    fun minSymbols(minSymbols: Int): InputValidator {
        return this
    }

    fun maxSymbols(maxSymbols: Int): InputValidator {
        return this
    }

    fun containsNumbers(): InputValidator {
        return this
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessages: List<String>
        )
}