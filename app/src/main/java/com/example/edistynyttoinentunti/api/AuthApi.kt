package com.example.edistynyttoinentunti.api

// Retrofit client


import com.example.edistynyttoinentunti.model.LoginReqModel
import com.example.edistynyttoinentunti.model.LoginResModel
import com.example.edistynyttoinentunti.model.RegisterReqModel
import com.example.edistynyttoinentunti.model.RegisterResModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private val retrofitClient = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8000/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

// Tätä käytetään viewModelissa, jotta voidaan tehdä requesti
val authService = retrofitClient.create(AuthApi::class.java)


interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body req: LoginReqModel) : LoginResModel

    // Kun kirjaudutaan ulos niin haetaan headerissa token, jotta tiedetään kuka kirjataan ulos
    // HUOM KÄYTETÄÄN TÄTÄ MYÖS MUIDEN KUTSUJEN KANSSA VAIKKA ONKIN LOGOUT, JOTTA EI TARVITSE MUUTTAA NIMEÄ.
    @POST("auth/logout")
    suspend fun logout(@Header("Authorization:") bearerToken: String)

    // Kutsu rekisteröintiä varten
    @POST("auth/register")
    suspend fun register(@Body req: RegisterReqModel) : RegisterResModel


}

