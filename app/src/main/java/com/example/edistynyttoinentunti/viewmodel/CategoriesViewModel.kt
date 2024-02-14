package com.example.edistynyttoinentunti.viewmodel

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.model.CategoriesState
import com.example.edistynyttoinentunti.model.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {


    // Tänne yhdistetään seuraavalla tunnilla Categories.kt tiedostossa olevat luokat
    //  Tässä pitää näkyä lista kategorioista, joita meillä tulee olemaan meidän softassa


    private val _categoriesState = mutableStateOf(CategoriesState())

    // Julkinen vastine jotta voidaan käyttää composablessa
    val categoriesState: State<CategoriesState> = _categoriesState

    init {
        // Init on blokki, joka voidaan ajaa primary contruktorin ajamisen jälkeen.
        // Täällä tehdään myös rajapintakutsut. Tämä pyöritetään, heti näytön käynnistyttyä. (Tälle näytölle haetaan dataa tietokannasta.)
        // Täällä voidaan kutsua getCategories()-fukntiota ilman, että tulee ikilooppi.
        getCategories()
    }


    private suspend fun waitForCategories() {
        delay(2000)
    }


    // Haetaan kategorioita
    private fun getCategories() {

        viewModelScope.launch {
            _categoriesState.value = _categoriesState.value.copy(loading = true) // Tämä on false Categories.kt puolella
            waitForCategories() // Odotetaan 2 sekunttia
            // Data tulee sisään. Huom käytetään luokkaa Categories.kt puolelta
            _categoriesState.value = categoriesState.value.copy(
                loading = false,
                list = listOf(
                    CategoryItem(id = 1, name = "Kateogoria1"),
                    CategoryItem(id = 2, name = "Kategoria2"))
            )
        }
    }


}