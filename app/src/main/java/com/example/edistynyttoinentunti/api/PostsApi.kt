package com.example.edistynyttoinentunti.api

import com.example.edistynyttoinentunti.model.Post
import retrofit2.http.GET


// Tässä kutsutaan Client.kt tiedostossa olevaa funktiota.
private val retrofit = createClient()


val postsService = retrofit.create(PostsApi::class.java)


interface PostsApi {
    // Annonaatio siitä minkälaista requestia ollaan tekemässä. Tämä on siis URLin jatko. Tämä tulee baseurlin jatkeeksi.
    @GET("posts")

    // Koska yllä oleva metodi voi epäonnistua niin pitää tehdä suspend.
    // : jälkeen tulee Model\Posts.kt tiedostoon ilmoitettu datan muotu mitä haetaan
    // List<Post> kertoo, että requestin myötä tulee listassa tietoa, joka on samassa muodossa Post classin tiedon kanssa
    suspend fun getPosts(): List<Post>
    // getPosts() funktiota tullaan kutsumaan vievmodelista
}