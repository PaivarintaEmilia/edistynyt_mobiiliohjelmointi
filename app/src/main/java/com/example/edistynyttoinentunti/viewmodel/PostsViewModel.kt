package com.example.edistynyttoinentunti.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.postsService
import com.example.edistynyttoinentunti.model.PostsState
import kotlinx.coroutines.launch
import java.lang.Exception


// Eli täällä on tarkoitus kutsua api\PostsApi.kt tiedostossa olevaa funktiota getPosts()

class PostsViewModel: ViewModel() {

    // Tämä privaatti muuttuja staten kanssa tehdään aina ViewModeliin
    // Eli model\Posts.kt tiedostosta saadaan tieto minkä tyyppistä tietoa tässä muuttujassa tulee olla (class PostsState)
    private val _postsState = mutableStateOf(PostsState())

    val postsState: State<PostsState> = _postsState // Android.x.composable importti


    // Tehdään sama initti mikä CategoriesViewModelissa
    init {
        getPosts()
    }

    // Nyt itse funktio, joka hakee datan
    private fun getPosts() {

        // Suspendattu fukntio, jolla haetaan dataa
        viewModelScope.launch {
            try {
                // Tämä on siis, kun ladataan jotain. Mitä?
                _postsState.value = _postsState.value.copy(loading = true)
                // Yritetään tehdä hakua
                val response = postsService.getPosts() // Nämä molemmat tulevat api\PostsApi.kt
                // Responsen datan päivitys. Response sisältää listan postauksia.
                _postsState.value = _postsState.value.copy(list = response)
            } catch (e: Exception) {
                _postsState.value = _postsState.value.copy(error = e.toString())
            } finally {
                // Tämä ajetaan aina
                // Muutetaan falseksi niin ruudulle tulostetaan joko requesti tai virheviesti
                _postsState.value = _postsState.value.copy(loading = false)
            }
        }
    }


}