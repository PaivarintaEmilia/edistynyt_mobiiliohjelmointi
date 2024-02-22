package com.example.edistynyttoinentunti.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createClient(): Retrofit {
    // Rakennetaan HTTP clientti Retrofitillä. Opettaja sai tämän netistä.
    // Tätä voidaan käyttää myös ulkoisien sivustojen kanssa.
    // 10.0.2.2 on androidin oma alias tälle minun omalle koneelle.
    return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1/").addConverterFactory(GsonConverterFactory.create()).build()
}

// Tänne ei tule muuta. Tarkoitus on APIin liittyvissä asioissa kutsua tätä funktiota.