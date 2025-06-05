package com.example.myshoppingadminapp.presentation.Screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppingadminapp.common.State
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

    val addCategory = _addCategoryState.asStateFlow()
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
}

data class addCategoryState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""

)