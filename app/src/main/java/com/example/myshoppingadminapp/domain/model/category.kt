package com.example.myshoppingadminapp.domain.model

data class category(

    var name: String = "",
    var date: Long = System.currentTimeMillis(),
    var imageUri : String = ""
)