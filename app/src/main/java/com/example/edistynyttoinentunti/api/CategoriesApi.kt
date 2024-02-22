package com.example.edistynyttoinentunti.api

import com.example.edistynyttoinentunti.model.CategoriesResponse
import com.example.edistynyttoinentunti.model.Post
import retrofit2.http.GET


// Tässä kutsutaan Client.kt tiedostossa olevaa funktiota.
private val retrofit = createClient()


val categoriesService = retrofit.create(CategoriesApi::class.java)


interface CategoriesApi {
    // Annonaatio siitä minkälaista requestia ollaan tekemässä. Tämä on siis URLin jatko. Tämä tulee baseurlin jatkeeksi.
    @GET("category/")

    // Koska yllä oleva metodi voi epäonnistua niin pitää tehdä suspend.
    // : jälkeen tulee Model\Categories.kt tiedostoon ilmoitettu datan muotu mitä haetaan
    suspend fun getCategories(): CategoriesResponse
    // getCategories() funktiota tullaan kutsumaan vievmodelista
}