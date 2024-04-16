package com.example.edistynyttoinentunti.model


data class RegisterReqModel(
    val err: String? = null,
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val logOk: Boolean = false // Katso tehdäänkö tämä
)
