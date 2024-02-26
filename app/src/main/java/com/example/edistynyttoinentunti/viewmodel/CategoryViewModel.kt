package com.example.edistynyttoinentunti.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.edistynyttoinentunti.model.CategoryState

class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0 // MITÄ TARKOITTAAA?? JOTAIN TEKEMISTÄ NAVIGOINNIN KANSSA

    private val _categoryState = mutableStateOf(CategoryState()) // category tulee categories.kt
    val categoryState: State<CategoryState> = _categoryState // ks mitä nämä tarkoittaa!!


    init {
        Log.d("Emilia", "$categoryId")
    }
}