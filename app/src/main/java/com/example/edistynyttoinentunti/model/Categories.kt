package com.example.edistynyttoinentunti.model

import com.google.gson.annotations.SerializedName

// Lista kategorioita
// Onko loading true or false



// Meillä on lista yksittäisiä CategoryItemeitä
// Err on virheen catchaamista varten. ks. String? mitä tarkoittaa
data class CategoriesState(
    val list: List<CategoryItem> = emptyList(),
    val loading: Boolean = false,
    val err: String? = null
)


// editcategoryn vuoksi tehdään tämä!!!!!!!!
data class CategoryState(
    val item: CategoryItem = CategoryItem(),
    val loading: Boolean = false,
    val err: String? = null
)


// Mitä yksittäinen kategoria pitää sisällään
data class CategoryItem(
    // Serialized name liittyy pythonin ja kotlinin nimeämiskäytäntöihin
    @SerializedName("category_id")
    val id: Int = 0,
    @SerializedName("category_name")
    val name: String = ""
)


// Tämä tehdään, jotta APIssa CategoriesApi tietää odottavansa oikeanmuotoista dataa.
// Datassa on id:n ja stringin lisäksi otsikko. Tämän takia yllä oleva CategoriesItem on lisätty listaan categories..
data class CategoriesResponse(val categories: List<CategoryItem> = emptyList())