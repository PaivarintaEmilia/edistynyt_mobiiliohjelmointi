package com.example.edistynyttoinentunti.model

// Tämä kuulemma on esimerkki datan hausta tunnilla käytetystä sivustosta
// Tämän tiedoston tarkoituksena on kertoa applikaatiolle, että minkälaista dataa..
// ..tehdystä Post requestista tulee odottaa.
// Tällä sivustolla on esimerkki https://jsonplaceholder.typicode.com/posts

data class Post(val userId: Int, val id: Int, val title: String, val body: String)


// Tämä lisätään PostsViewModelia varten. Jotta ensimmäinen privaatti muuttuja toimii.
// Eli listaus postauksia, joka on oletukseltaan tyhjä. Alun loading on oletuksena false. Lisäksi koska Post-requesti kyseessä niin lisätään mahdollinen error
data class PostsState(val list: List<Post> = emptyList(), val loading: Boolean = false, val error: String? = null)