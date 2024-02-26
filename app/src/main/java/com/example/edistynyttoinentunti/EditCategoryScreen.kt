package com.example.edistynyttoinentunti

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.viewmodel.CategoryViewModel

@Composable
fun EditCategoryScreen() {

    // kutsutaan viewmodelia
    val vm: CategoryViewModel = viewModel()

}