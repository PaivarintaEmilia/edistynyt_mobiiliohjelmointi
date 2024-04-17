package com.example.edistynyttoinentunti.model

import com.google.gson.annotations.SerializedName


data class RegisterReqModel(
    val err: String? = null,
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val registerOk: Boolean = false
)

data class RegisterResModel(
    @SerializedName("auth_user_id") // Otetaan tämä tieto, jos tätä voi käyttää jossain?
    val authUserId: Int = 0
)
