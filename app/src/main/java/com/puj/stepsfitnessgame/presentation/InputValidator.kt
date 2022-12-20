package com.puj.stepsfitnessgame.presentation

class InputValidator(private val validatedValue: String) {

    private var isValid = true
    private val errorMessages = ArrayList<String>()

    fun validate(): ValidationResult {
        return ValidationResult(isValid, errorMessages)
    }

    fun isEmail(): InputValidator {
        val isValueEmail = validatedValue.contains(emailPattern)
        setValidity(isValueEmail)
        return this
    }

    fun isNotBlank(): InputValidator {
        setValidity(validatedValue.isNotBlank())
        return this
    }

    fun hasUpperCase(): InputValidator {
        var hasUpperCase = false
        validatedValue.forEach {
            if(it.isUpperCase()) {
                hasUpperCase = true
            }
        }
        setValidity(hasUpperCase)
        return this
    }

    fun hasLowerCase(): InputValidator {
        var hasLowerCase = false
        validatedValue.forEach {
            if(it.isLowerCase()) {
                hasLowerCase = true
            }
        }
        setValidity(hasLowerCase)
        return this
    }

    fun minSymbols(minSymbols: Int): InputValidator {
        val amountOfSymbols = validatedValue.length
        val isMoreThanMinimum = amountOfSymbols >= minSymbols
        setValidity(isMoreThanMinimum)
        return this
    }

    fun maxSymbols(maxSymbols: Int): InputValidator {
        val amountOfSymbols = validatedValue.length
        val isLessThanMaximum = amountOfSymbols <= maxSymbols
        setValidity(isLessThanMaximum)
        return this
    }

    fun hasNumbers(): InputValidator {
        val hasNumbers = validatedValue.contains(numbers)
        setValidity(hasNumbers)
        return this
    }

    private fun setValidity(validationResult: Boolean) {
        isValid = if(isValid){
            validationResult
        }
        else {
            false
        }
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessages: List<String>
        )

    companion object {
        private val numbers = Regex("\\d+")

        private val emailPattern = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }
}