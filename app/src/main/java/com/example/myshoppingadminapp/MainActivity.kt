package com.example.myshoppingadminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingadminapp.presentation.Screens.MyViewModel
import com.example.myshoppingadminapp.presentation.Screens.addCategoryScreen
import com.example.myshoppingadminapp.presentation.Screens.addProductScreen
import com.example.myshoppingadminapp.ui.theme.MyShoppingAdminAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingAdminAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //addCategoryScreen()
                    addProductScreen(
                        onBack = {}
                    )
                }
            }
        }
    }
}
