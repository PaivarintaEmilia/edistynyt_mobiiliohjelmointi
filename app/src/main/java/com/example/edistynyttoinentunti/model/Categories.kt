package com.example.edistynyttoinentunti.model

// Lista kategorioita
// Onko loading true or false



// Meillä on lista yksittäisiä CategoryItemeitä
data class CategoriesState(val list: List<CategoryItem> = emptyList(), val loading: Boolean = false)


// Mitä yksittäinen kategoria pitää sisällään
data class CategoryItem(val id: Int = 0, val name: String = "")