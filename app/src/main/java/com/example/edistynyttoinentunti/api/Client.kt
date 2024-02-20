package com.example.edistynyttoinentunti.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createClient(): Retrofit {
    // Rakennetaan HTTP clientti Retrofitillä. Opettaja sai tämän netistä.
    // Tätä voidaan käyttää myös ulkoisien sivustojen kanssa.
    return Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build()
}

// Tänne ei tule muuta. Tarkoitus on APIin liittyvissä asioissa kutsua tätä funktiota.