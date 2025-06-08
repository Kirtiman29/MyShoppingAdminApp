package com.example.myshoppingadminapp.presentation.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.myshoppingadminapp.domain.model.ProductDataModel


@Composable
fun addProductScreen(
    viewModel: MyViewModel = hiltViewModel()
) {
    val productState = viewModel.addProducts.collectAsState()
    val uploadProductImageState = viewModel.uploadProductImage.collectAsState()
    val getCategoriesState = viewModel.getCategories.collectAsState()
    val productName = remember { mutableStateOf("") }
    val productDescription = remember { mutableStateOf("") }
    val productPrice = remember { mutableStateOf("") }
    val productFinalPrice = remember { mutableStateOf("") }
    val productCategory = remember { mutableStateOf("") }
    val productImage = remember { mutableStateOf("") }
    val productAvailableUnits = remember { mutableStateOf("") }
    val productIsAvailable = remember { mutableStateOf(true) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf("") }


    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                imageUri = it
                viewModel.uploadProdImage(it)
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    when{

        uploadProductImageState.value.message.isNotBlank() -> {
            imageUrl = uploadProductImageState.value.message
        }
    }


        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Add Image",
                        modifier = Modifier.clickable{

                        })
                    Text(text = "Add Image")
                }

            }
        }

        OutlinedTextField(
            value = productName.value,
            onValueChange = { productName.value = it },
            label = {
                Text(text = "Product Name")
            }

        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productDescription.value,
            onValueChange = { productDescription.value = it },
            label = {
                Text(text = "Product Description")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productPrice.value,
            onValueChange = { productPrice.value = it },
            label = {
                Text(text = "Product Price")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productFinalPrice.value,
            onValueChange = { productFinalPrice.value = it },
            label = {
                Text(text = "Product Final Price")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productCategory.value,
            onValueChange = { productCategory.value = it },
            label = {
                Text(text = "Product Category")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { },
            label = {
                Text(text = "Product Image")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productAvailableUnits.value,
            onValueChange = { productAvailableUnits.value = it },
            label = {
                Text(text = "Product Available Units")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productIsAvailable.value.toString(),
            onValueChange = { productIsAvailable.value = it.toBoolean() },
            label = {
                Text(text = "Product Is Available")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val data = ProductDataModel(
                    name = productName.value,
                    description = productDescription.value,
                    price = productPrice.value,
                    finalprice = productFinalPrice.value,
                    category = productCategory.value,
                    imageUri = productImage.value,
                    availableUnits = productAvailableUnits.value.toInt(),
                    isAvailable = productIsAvailable.value

                )
                viewModel.addProducts(data)
            }
        ) {
            Text(text = "Add Product")
        }


    }

}