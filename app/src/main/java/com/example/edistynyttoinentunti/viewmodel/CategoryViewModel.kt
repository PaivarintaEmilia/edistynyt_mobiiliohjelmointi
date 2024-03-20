package com.example.edistynyttoinentunti.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.categoriesService
import com.example.edistynyttoinentunti.model.CategoryState
import com.example.edistynyttoinentunti.model.EditCategoryReq
import kotlinx.coroutines.launch

class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    // SavedHandlen avulla saadaan valitun categoryn id
    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0 // MITÄ TARKOITTAAA?? JOTAIN TEKEMISTÄ NAVIGOINNIN KANSSA

    // categooryState on olio, joka sisältää yksittäisen kategorian tiedot
    private val _categoryState = mutableStateOf(CategoryState()) // category tulee categories.kt
    val categoryState: State<CategoryState> = _categoryState


    init {
        // Tässä tulee kutsua getCategory() funktiota. Muuten se ei näy käyttöliittymässä.
        // Funktiossa ymmärtääkseni haetaan kategorian nimi ja id
        getCategory()

        Log.d("Emilia", "$_categoryId")
    }


    // Tämä funktio tekee itse kategorian nimen vaihdon, kun se halutaan muuttaa EditCategoryScreenissa.
    // Muuttaa nimen titleen ja tekstikenttään. (EI TIETOKANTAAN)
    fun setName(newName: String) {
        // Päivitetään nimi
        val item = _categoryState.value.item.copy(name=newName)
        // Asetetaan uusi kategorian nimi stateen
        _categoryState.value = _categoryState.value.copy(item = item)

    }

    // Tämä funktio tehdään, jotta editScreenissa nappia Edit painamalla päästään takaisin kategorioiden listaus screenille
    // vai onko tämä sittenkin, sitä varten, että saadaan muokattua db:ssä myös kategorian nimi?
    fun editCategory(goToCategories : () -> Unit) {
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                // Mikä tämä on ja mistä tämä koostuu?
                categoriesService.editCategory(_categoryId, EditCategoryReq(name=_categoryState.value.item.name))
                Log.d("Emilia", "done")
                goToCategories()
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(err = e.toString())
            }finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }

    // Luodaan funktio, jolla haetaan kategoria, jonka nimeä halutaan muuttaa EditCategoryScreenissa
    private fun getCategory() {
        // Viewmodelin sisällä tulee aina tehdä launchaus
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                // Kategorian haku api kyselyllä. CategoriesApi + Categories tiedostot
                val res = categoriesService.getCategory(_categoryId)
                // Eso qué es??
                _categoryState.value = _categoryState.value.copy(item = res.category)
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(err = e.toString())
            } finally {
                // Finalyssa, kävi miten kävi niin muutetaan loading falseksi
                _categoryState.value = _categoryState.value.copy(loading = false)
            }

        }
    }
}