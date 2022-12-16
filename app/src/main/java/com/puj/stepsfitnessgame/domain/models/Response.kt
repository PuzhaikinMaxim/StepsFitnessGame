package com.puj.stepsfitnessgame.domain.models

sealed class Response<T>() {

    class Success<T>(val data: T) : Response<T>()

    class Error<T>(val errorCode: Int) : Response<T>()
}