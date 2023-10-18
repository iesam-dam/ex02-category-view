package com.iesam.androidviews.app

sealed class ErrorApp {
    object DataError : ErrorApp()
    object NetworkError : ErrorApp()
    object UnknowError : ErrorApp()
}