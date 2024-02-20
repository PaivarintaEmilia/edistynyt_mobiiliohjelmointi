package com.example.edistynyttoinentunti

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.viewmodel.PostsViewModel

// Composable komponentti missä esitetään data. Eli mitä näytetään näytöllä.
// PostsScreen() funktiota tulee kutsua navHostissa.
@Composable
fun PostsScreen() {
    // Kutsutaan viewModelia. Eli tämä siis kutsuu init blokkia tiedostosta PostsViewModel.kt
    val postsVm: PostsViewModel = viewModel()

    // käyttöliittymäkikkare :D
    // Huomaa, että postsState on postsState muuttuja viewModelista!
    // yksittäinen muutu

    LazyColumn() {
        items(postsVm.postsState.value.list) {
            // Yksittäisen postauksen ulkoasu
            Text(text = it.title)
        }
    }
}