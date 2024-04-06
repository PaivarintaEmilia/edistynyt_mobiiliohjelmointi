package com.example.edistynyttoinentunti.api

// Retrofit client


import com.example.edistynyttoinentunti.model.LoginReqModel
import com.example.edistynyttoinentunti.model.LoginResModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private val retrofitClient = Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1/").addConverterFactory(GsonConverterFactory.create()).build()

// Tätä käytetään viewModelissa, jotta voidaan tehdä requesti
val authService = retrofitClient.create(AuthApi::class.java)


interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body req: LoginReqModel) : LoginResModel

}