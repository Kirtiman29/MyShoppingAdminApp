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
class MyViewModel @Inject constructor(
    private val repo: repo // Capitalized interface name
) : ViewModel() {

    // States should use proper naming convention (capitalized)
    private val _addCategoryState = MutableStateFlow(AddCategoryState())
    val addCategory = _addCategoryState.asStateFlow()

    private val _getCategoriesState = MutableStateFlow(GetCategoryState())
    val getCategories = _getCategoriesState.asStateFlow()

    private val _addProductsState = MutableStateFlow(AddProductsState())
    val addProducts = _addProductsState.asStateFlow()

    private val _uploadProductImageState = MutableStateFlow(UploadProductImageState())
    val uploadProductImage = _uploadProductImageState.asStateFlow()

    fun uploadProdImage(imageUri: Uri) {
        viewModelScope.launch {
            repo.uploadImage(image = imageUri).collectLatest { state ->
                _uploadProductImageState.value = when(state) {
                    is State.Loading -> UploadProductImageState(isLoading = true)
                    is State.Success -> UploadProductImageState(imageUrl = state.data)
                    is State.Error -> UploadProductImageState(error = state.error)
                }
            }
        }
    }

    fun addCategory(category: category) { // Capitalized model name
        viewModelScope.launch {
            repo.addCategory(category = category).collectLatest { state ->
                _addCategoryState.value = when(state) {
                    is State.Loading -> AddCategoryState(isLoading = true)
                    is State.Success -> AddCategoryState(successMessage = state.data)
                    is State.Error -> AddCategoryState(error = state.error)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            repo.getCategories().collectLatest { state ->
                _getCategoriesState.value = when(state) {
                    is State.Loading -> GetCategoryState(isLoading = true)
                    is State.Success -> GetCategoryState(categories = state.data)
                    is State.Error -> GetCategoryState(error = state.error)
                }
            }
        }
    }

    fun addProducts(productDataModel: ProductDataModel) {
        viewModelScope.launch {
            repo.addProducts(productDataModel).collectLatest { state ->
                _addProductsState.value = when(state) {
                    is State.Loading -> AddProductsState(isLoading = true)
                    is State.Success -> AddProductsState(successMessage = state.data)
                    is State.Error -> AddProductsState(error = state.error)
                }
            }
        }
    }

    // Add reset functions for states
    fun resetAddCategoryState() {
        _addCategoryState.value = AddCategoryState()
    }

    fun resetAddProductState() {
        _addProductsState.value = AddProductsState()
    }
}

// Improved state classes with better field names
data class AddCategoryState(
    val isLoading: Boolean = false,
    val successMessage: String = "",
    val error: String = ""
)

data class GetCategoryState(
    val isLoading: Boolean = false,
    val categories: List<category> = emptyList(), // Better than using toString()
    val error: String = ""
)

data class AddProductsState(
    val isLoading: Boolean = false,
    val successMessage: String = "",
    val error: String = ""
)

data class UploadProductImageState(
    val isLoading: Boolean = false,
    val imageUrl: String = "", // More specific than 'message'
    val error: String = ""
)