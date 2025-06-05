package com.example.myshoppingadminapp.common

sealed class State<out T>{

    data class Success<T>(var data: T):State<T>()

    data class Error(val error: String):State<Nothing>()
    object Loading:State<Nothing>()
}