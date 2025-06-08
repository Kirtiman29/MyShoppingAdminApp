package com.example.myshoppingadminapp.domain.repo

import com.example.myshoppingadminapp.common.State
import com.example.myshoppingadminapp.domain.model.ProductDataModel
import com.example.myshoppingadminapp.domain.model.category
import kotlinx.coroutines.flow.Flow

interface repo {

    fun addCategory(category: category):Flow<State<String>>

    fun getCategories() : Flow<State<List<category>>>

    fun addProducts(ProductDataModel: ProductDataModel): Flow<State<String>>
}