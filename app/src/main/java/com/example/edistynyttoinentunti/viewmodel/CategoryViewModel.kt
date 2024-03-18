package com.example.edistynyttoinentunti.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.categoriesService
import com.example.edistynyttoinentunti.model.CategoryState
import kotlinx.coroutines.launch

class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    // SavedHandlen avulla saadaan valitun categoryn id
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0 // MITÄ TARKOITTAAA?? JOTAIN TEKEMISTÄ NAVIGOINNIN KANSSA

    // categooryState on olio, joka sisältää yksittäisen kategorian tiedot
    private val _categoryState = mutableStateOf(CategoryState()) // category tulee categories.kt
    val categoryState: State<CategoryState> = _categoryState


    init {
        Log.d("Emilia", "$categoryId")
    }

    // Luodaan funktio, jolla haetaan kategoria, jonka nimeä halutaan muuttaa EditCategoryScreenissa
    private fun getCategory() {
        // Viewmodelin sisällä tulee aina tehdä launchaus
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                // Kategorian haku api kyselyllä. CategoriesApi + Categories tiedostot
                val res = categoriesService.getCategory(categoryId)
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(err = e.toString())
            } finally {
                // Finalyssa, kävi miten kävi niin muutetaan loading falseksi
                _categoryState.value = _categoryState.value.copy(loading = false)
            }

        }
    }
}