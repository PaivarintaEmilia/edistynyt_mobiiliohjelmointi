package com.example.edistynyttoinentunti.api

import com.example.edistynyttoinentunti.model.CategoriesResponse
import com.example.edistynyttoinentunti.model.CategoryResponse
import com.example.edistynyttoinentunti.model.EditCategoryReq
import com.example.edistynyttoinentunti.model.Post
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


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


    // Tehdään kysely fastApiin, jolla saadaan kategorian id. Tätä tarvitaan, kun halutaan
    // ..tietää categorian id, jonka nimi muutetaan EditCategoryScreen.kt tiedostossa.
    // Palauttaa yksittäisen categorian datan. (vai pekän id:n?)
    @GET("category/{id}")
    suspend fun getCategory(@Path("id") id: Int): CategoryResponse


    // Yksittäisen kategorian poisto. Tätä kutsutaan categoriesvm:ssä
    @DELETE("category/{id}")
    suspend fun removeCategory(@Path("id") id: Int)


    // Tämä luodaan, jotta saadaan vaihdettua categorian nimi myös DB:ssä asti. Kategorian nimi vaihdetaan
    // EditCategoriesName.kt tiedostossa.
    // @Body tarkoittee, että requesti ottaa bodysta tätä seuraavan muuttujan
    @PUT("category({id}")
    suspend fun editCategory(@Path("id") id: Int, @Body editCategoryReq: EditCategoryReq) : CategoryResponse



}