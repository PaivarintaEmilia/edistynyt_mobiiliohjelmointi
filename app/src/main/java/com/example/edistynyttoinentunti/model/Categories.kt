package com.example.edistynyttoinentunti.model

// Lista kategorioita
// Onko loading true or false



// Meillä on lista yksittäisiä CategoryItemeitä
data class CategoriesState(val list: List<CategoryItem> = emptyList(), val loading: Boolean = false)


// Mitä yksittäinen kategoria pitää sisällään
data class CategoryItem(val category_id: Int = 0, val category_name: String = "")


// Tämä tehdään, jotta APIssa CategoriesApi tietää odottavansa oikeanmuotoista dataa.
// Datassa on id:n ja stringin lisäksi otsikko. Tämän takia yllä oleva CategoriesItem on lisätty listaan categories..
data class CategoriesResponse(val categories: List<CategoryItem> = emptyList())