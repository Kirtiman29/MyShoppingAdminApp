package com.example.myshoppingadminapp.presentation.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.myshoppingadminapp.domain.model.ProductDataModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addProductScreen(
    viewModel: MyViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Form state
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productFinalPrice by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productAvailableUnits by remember { mutableStateOf("") }
    var productIsAvailable by remember { mutableStateOf(true) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // State from ViewModel
    val uploadState = viewModel.uploadProductImage.collectAsState()
    val addProductState = viewModel.addProducts.collectAsState()
    val categoriesState = viewModel.getCategories.collectAsState()

    // Image picker
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            imageUri = it
            viewModel.uploadProdImage(it)
        }
    }

    // Handle side effects
    LaunchedEffect(uploadState.value.imageUrl) {
        uploadState.value.imageUrl.let { url ->
            // Image uploaded successfully
            Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(addProductState.value.successMessage) {
        addProductState.value.successMessage.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            onBack() // Navigate back after success
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Loading indicator
//            if (uploadState.value.isLoading || addProductState.value.isLoading) {
//                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//            }

            // Image upload section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        imagePicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Product Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Image"
                        )
                        Text("Add Product Image")
                    }
                }
            }

            // Error messages
            uploadState.value.error?.let { error ->
                Text(
                    text = "Image Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // Form fields
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
            OutlinedTextField(
                value = productCategory,
                onValueChange = { productCategory = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Price") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = productFinalPrice,
                    onValueChange = { productFinalPrice = it },
                    label = { Text("Final Price") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // Category dropdown
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
//                OutlinedTextField(
//                    value = productCategory,
//                    onValueChange = {},
//                    label = { Text("Category") },
//                    modifier = Modifier.fillMaxWidth(),
//                    readOnly = true,
//                    trailingIcon = {
//                        Icon(Icons.Default.ArrowDropDown, null)
//                    },
//                    onClick = { expanded = true }
//                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categoriesState.value.categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                productCategory = category.name
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = productAvailableUnits,
                onValueChange = { productAvailableUnits = it },
                label = { Text("Available Units") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Switch(
                    checked = productIsAvailable,
                    onCheckedChange = { productIsAvailable = it }
                )
                Text("Product Available")
            }

            // Submit button
            Button(
                onClick = {
                    if (imageUri == null) {
                        Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (uploadState.value.imageUrl == null) {
                        Toast.makeText(context, "Please wait for image to upload", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val product = ProductDataModel(
                        name = productName,
                        description = productDescription,
                        price = productPrice,
                        finalprice = productFinalPrice,
                        category = productCategory,
                        imageUri = imageUri.toString(),
                        availableUnits = productAvailableUnits.toIntOrNull() ?: 0,
                        isAvailable = productIsAvailable
                    )

                    viewModel.addProducts(product)
                },
                modifier = Modifier.fillMaxWidth(),
                //enabled = !addProductState.value.isLoading && !uploadState.value.isLoading
            ) {
                Text("Add Product")
            }

            // Show errors
            addProductState.value.error?.let { error ->
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}