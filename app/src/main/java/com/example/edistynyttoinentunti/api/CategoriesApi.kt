package com.example.edistynyttoinentunti.api

import com.example.edistynyttoinentunti.model.AddCategoryReq
import com.example.edistynyttoinentunti.model.CategoriesResponse
import com.example.edistynyttoinentunti.model.CategoryItem
import com.example.edistynyttoinentunti.model.CategoryResponse
import com.example.edistynyttoinentunti.model.EditCategoryReq
import com.example.edistynyttoinentunti.model.Post
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


// Tässä kutsutaan Client.kt tiedostossa olevaa funktiota.
private val retrofit = createClient()


val categoriesService = retrofit.create(CategoriesApi::class.java)


interface CategoriesApi {
    // Annonaatio siitä minkälaista requestia ollaan tekemässä. Tämä on siis URLin jatko. Tämä tulee baseurlin jatkeeksi.
    // TÄLLÄ HAETAAN KAIKKI KATEGORIAT
    @GET("category/")
    // Koska yllä oleva metodi voi epäonnistua niin pitää tehdä suspend.
    // : jälkeen tulee Model\Categories.kt tiedostoon ilmoitettu datan muotu mitä haetaan
    suspend fun getCategories(): CategoriesResponse
    // getCategories() funktiota tullaan kutsumaan vievmodelista


    // Tehdään kysely fastApiin, jolla saadaan kategorian id. Tätä tarvitaan, kun halutaan
    // ..tietää categorian id, jonka nimi muutetaan EditCategoryScreen.kt tiedostossa.
    // Palauttaa yksittäisen categorian datan. (vai pekän id:n?)
    // TÄLLÄ HAETAAN VAIN YKSI KATEGORIA
    @GET("category/{id}")
    suspend fun getCategory(@Path("id") id: Int): CategoryResponse


    // Yksittäisen kategorian poisto. Tätä kutsutaan categoriesvm:ssä
    // TÄLLÄ POISTETAAN YKSI KATEGORIA
    @DELETE("category/{id}")
    suspend fun removeCategory(@Path("id") id: Int)


    // Tämä luodaan, jotta saadaan vaihdettua categorian nimi myös DB:ssä asti. Kategorian nimi vaihdetaan
    // EditCategoriesName.kt tiedostossa.
    // @Body tarkoittee, että requesti ottaa bodysta tätä seuraavan muuttujan
    // TÄLLÄ PÄIVITETÄÄN YKSI KATEGORIA
    @PUT("category/{id}")
    suspend fun editCategory(@Path("id") id: Int, @Body editCategoryReq: EditCategoryReq) : CategoryResponse


    // TÄLLÄ TALLENNETAAN UUSI KATEGORIA @POST
    @POST("category/")
    suspend fun createCategory(@Body req: AddCategoryReq) : CategoryItem


    // TESTI itemien listaamista kategorioittain
    //@GET("category/{category_id}/items")
    //suspend fun getCategoriesSortedByitems(@Path("id") id: Int): ItemCategoryResponse



}