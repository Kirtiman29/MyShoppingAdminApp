package com.example.myshoppingadminapp.domain.model

data class ProductDataModel (
    val name: String = "",
    val description : String = "",
    val price: String = "",
    val finalprice: String = "",
    val category: String = "",
    val imageUri : String = "",
    val date: Long = System.currentTimeMillis(),
    val availableUnits: Int = 0,
    val isAvailable: Boolean = true
)