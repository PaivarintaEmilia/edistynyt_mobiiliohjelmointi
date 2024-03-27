package com.example.edistynyttoinentunti.viewmodel

import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.categoriesService
import com.example.edistynyttoinentunti.model.AddCategoryState
import com.example.edistynyttoinentunti.model.CategoriesState
import com.example.edistynyttoinentunti.model.CategoryItem
import com.example.edistynyttoinentunti.model.DeleteCAtegoryState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    // NÄMÄ STATET KOSKETTAVAT CATEGORIES-LISTAA
    // Tänne yhdistetään seuraavalla tunnilla Categories.kt tiedostossa olevat luokat
    //  Tässä pitää näkyä lista kategorioista, joita meillä tulee olemaan meidän softassa
    private val _categoriesState = mutableStateOf(CategoriesState())
    // Julkinen vastine jotta voidaan käyttää composablessa
    // pitää sisällään tiedot useista kategorioista, kuten niiden lataustilan ja mahdolliset virheet
    // Käytetään näyttämään kategorioiden lista käyttöliittymässä
    val categoriesState: State<CategoriesState> = _categoriesState


    // NÄMÄ STATET OVAT CATEGORIAN POISTON ALERT-VIESTIÄ VARTEN (Koska on eri käyttötapaus niin tehdään uusi state)
    private val _deleteCategoryState = mutableStateOf(DeleteCAtegoryState())
    // Julkinen (getteri privaatille statelle, luetaan dataa ei voida muuttaa)
    val deleteCategoryState: State<DeleteCAtegoryState> = _deleteCategoryState


    // STATET KATEGORIAN LISÄYSTÄ VARTEN
    private val _addCategoryState = mutableStateOf(AddCategoryState())
    val addCategoryState: State<AddCategoryState> = _addCategoryState



    init {
        // Init on blokki, joka voidaan ajaa primary contruktorin ajamisen jälkeen.
        // Täällä tehdään myös rajapintakutsut. Tämä pyöritetään, heti näytön käynnistyttyä. (Tälle näytölle haetaan dataa tietokannasta.)
        // Täällä voidaan kutsua getCategories()-fukntiota ilman, että tulee ikilooppi.
        getCategories()
    }


    // Uusi funktio, jolla päivitetään tekstikentän arvo, kun siihen kirjoitetaan alertissa uusi kategorian nimi
    fun setNAme(newName: String) {
        _addCategoryState.value = _addCategoryState.value.copy(name = newName)
    }


    // Tehdään funktio, jota kutsutaan, kun painetaan screenin + -painiketta. Aktivoidaan isAddingCategory-muuttuja (boolean)
    fun toggleAddCategory() {
        _categoriesState.value = _categoriesState.value.copy(isAddingCategory = !_categoriesState.value.isAddingCategory)
    }


    // _deleteCategoryStaten error tulee saada nulliksi tostin virheilmoitusta varten
    fun clearErr() {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(error = null)
    }


    // Tämä funktio on sitä varten, että saadaan asetettua poistettavan categoryn id stateen alert-viestiä varten
    fun verifyCategoryRemoval(categoryId: Int) {
        // Kun käyttöliittymässä painetaan roskis-iconia niin kutsutaan tätä funktiota
        // Muutetaan staten valueta niin, että id:stä tulee category id
        _deleteCategoryState.value = _deleteCategoryState.value.copy(id = categoryId)
    }


    // Tehdään funktio, jossa poistetaan category itemi listalta ihan vaan käyttöliittymästä
    // Tarvitaan poistettavan categorian id, jotta tiedetään mitä categoriaa ollaan poistamassa
    fun deleteCategoryById(categoryId: Int) {
        // Kutsutaan rajapintakutsua (vasta alertin vahvistuksen jälkeen)
        viewModelScope.launch {
            try {

                // Tehdään poisto requesti (Api-filessa) Deletestä ei tule responsea vain vastaus onko ok vai ei.
                // TÄMÄ ON DB:TÄ VARTEN
                categoriesService.removeCategory(categoryId)

                // TÄMÄ ON SITÄ VARTEN, ETTÄ SAADAAN KÄYTTÖLIITTYMÄSTÄ MYÖS POISTETTUA TÄMÄ
                // List sisältää kaikki category itemit listassa. Filtteröidään tämä lista.
                val listOfCategories = _categoriesState.value.list.filter {
                    // Yksittäinen category item on "it". Filtteri käy listan jokaisen itemin läpi. Jokaisella iteroinnilla it on tämän listan rown category
                    // Eli jos listan category itemin id ei ole poistettavan categoryn id niin se lisätään listaan. Poistettava jätetään pois
                    // Again --> Lista sisältää ne categoryItemit johon alla oleva ehto ei päde (lisätään siis listaan ja poistettava jää pois)
                    categoryId != it.id
                }

                // Asetetaan uuden listan arvot list-muuttujaan
                _categoriesState.value = _categoriesState.value.copy(list = listOfCategories)
                // --> Nyt kun painetaan Delete-painiketta niin näytölle tulostuu uusi lista ilman poistettua categorya

                // Kun poisto on onnistunut niin muutetaan deleteState 0:ksi
                // Koska ehto on: jos id on suurempi kuin 0 niin näytetään alert niin ei näytetä alerttia enää kun poisto ok
                _deleteCategoryState.value = _deleteCategoryState.value.copy(id = 0)



            } catch (e: Exception) {

                // Tässä vaiheessa vielä logitetaan vain poiston virheviesti
                Log.d("Emilia", e.toString() )

                // Tehdään jotta saadaan tost-virheviesti näkymään jos poiston yhteydessä tulee virhe
                // Jos tätä ei ole niin tost-viestiä ei näy ollenkaan.
                _deleteCategoryState.value = _deleteCategoryState.value.copy(error = e.toString())



            } finally {

                // Tähän tulee sitten jossain vaiheessa loading spinnerin falsettaminen
            }
        }

    }


    private suspend fun waitForCategories() {
        delay(2000)
    }


    // Haetaan kategorioita
    private fun getCategories() {

        viewModelScope.launch {
            try {
                _categoriesState.value = _categoriesState.value.copy(loading = true) // Tämä on false Categories.kt puolella
                val response = categoriesService.getCategories()
                // Data tulee sisään. Huom käytetään luokkaa Categories.kt puolelta
                _categoriesState.value = categoriesState.value.copy(
                    list = response.categories // Tämä tehtiin käsin tehdyn listan tilalle.
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(err=e.message)
            } finally {
                // Tämä tehdään joka tapauksessa tulee try tai catch
                // sama kuin loading = false
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }

        }
    }
}