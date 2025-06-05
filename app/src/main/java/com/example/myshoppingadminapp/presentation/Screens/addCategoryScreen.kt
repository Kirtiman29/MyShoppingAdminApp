package com.example.myshoppingadminapp.presentation.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingadminapp.domain.model.category


@Composable
fun addCategoryScreen(
    viewModel: MyViewModel = hiltViewModel()
) {
    val state = viewModel.addCategory.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val categoryName = remember { mutableStateOf("") }
        val categoryImage = remember { mutableStateOf("") }
        OutlinedTextField(
            value = categoryName.value,
            onValueChange = {categoryName.value = it},
            label = { Text(text = "Category Name") }
        )
        OutlinedTextField(
            value = categoryImage.value,
            onValueChange = {categoryImage.value = it},
            label = { Text(text = "Category ImageUrl") }
        )

        Button(
            onClick = {
                val data = category(

                    name = categoryName.value,
                    imageUri = categoryImage.value
                )
                viewModel.addCategory(data)

            }
        ) {
            Text(text = "Add Category")
        }
    }


}