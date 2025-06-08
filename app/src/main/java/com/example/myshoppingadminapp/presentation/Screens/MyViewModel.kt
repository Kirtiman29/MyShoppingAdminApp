package com.example.myshoppingadminapp.presentation.Screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppingadminapp.common.State
import com.example.myshoppingadminapp.domain.model.ProductDataModel
import com.example.myshoppingadminapp.domain.model.category
import com.example.myshoppingadminapp.domain.repo.repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel
    @Inject constructor(
        private val repo: repo
    )
    : ViewModel() {

    private var _addCategoryState = MutableStateFlow(addCategoryState())

    private var _getCategoriesState = MutableStateFlow(getCategoryState())

    private var _addProductsState = MutableStateFlow(addProductsState())

    val addCategory = _addCategoryState.asStateFlow()
    val getCategories = _getCategoriesState.asStateFlow()
    val addProducts = _addProductsState.asStateFlow()

    private var _uploadProductImageState = MutableStateFlow(uploadProductImageState())
    val uploadProductImage = _uploadProductImageState.asStateFlow()



    fun uploadProdImage(imageUri: Uri){
        viewModelScope.launch {
            repo.uploadImage(image=imageUri).collectLatest {
                when(it){
                    is State.Loading ->{
                        _uploadProductImageState.value = uploadProductImageState(isLoading = true)
                    }
                    is State.Success ->{
                        _uploadProductImageState.value = uploadProductImageState(message = it.data)
                }
                    is State.Error ->{
                        _uploadProductImageState.value = uploadProductImageState(error = it.error)
                    }
                }

            }

        }
    }

    fun addCategory(category: category) {

        viewModelScope.launch {
            repo.addCategory(category).collectLatest {
                when (it) {
                    is State.Loading -> {
                        _addCategoryState.value = addCategoryState(isLoading = true)
                    }

                    is State.Success -> {
                        _addCategoryState.value = addCategoryState(message = it.data)

                    }
                    is State.Error ->{
                        _addCategoryState.value = addCategoryState(error = it.error)
                    }
                }
            }

        }


    }

    fun getCategories(){
        viewModelScope.launch {
            repo.getCategories().collectLatest {
                when(it){
                    is State.Loading ->{
                        _getCategoriesState.value = getCategoryState(isLoading = true)
                    }
                    is State.Success ->{
                        _getCategoriesState.value = getCategoryState(message = it.data.toString())
                    }
                    is State.Error -> {
                        _getCategoriesState.value = getCategoryState(error = it.error)
                    }

                }
            }
        }
    }

    fun addProducts(ProductDataModel: ProductDataModel) {
        viewModelScope.launch {
            repo.addProducts(ProductDataModel).collectLatest {
                when(it){
                    is State.Loading ->{
                        _addProductsState.value = addProductsState(isLoading = true)
                    }
                    is State.Success ->{
                        _addProductsState.value = addProductsState(message = it.data)
                    }
                    is State.Error ->{
                        _addProductsState.value = addProductsState(error = it.error)

                    }
                }
            }
        }

    }
}

data class addCategoryState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""

)

data class getCategoryState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""

)


data class addProductsState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""

)

data class uploadProductImageState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)