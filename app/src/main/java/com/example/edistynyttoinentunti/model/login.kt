package com.example.edistynyttoinentunti.model

import com.google.gson.annotations.SerializedName


// Login sivun alku muuttujat
data class LoginReqModel(
    val err: String? = null,
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val logOk: Boolean = false // Muuttuja loginin tarkistukselle
)

// Vastaus, kun login-tiedot on syötetty ja painetaan login-painiketta
data class LoginResModel(
    val id: Int = 0, // Tämä on responsessa luotu id käyttäjälle. Oletusarvo on 0.
    @SerializedName("access_token")
    val accessToken: String = "", //Tämä tulee aina loginin yhteydessä. Vielä ei tarvitse tietää mistä on kyse.
    val username: String = "", // Tämän avulla voidaan luoda ilmoitus "Hei KÄyttäjä + username!"
    // Id kirjautumisen tunnistamista varten.
    @SerializedName("auth_user_id")
    val userId: Int = 0


)


// Logouttia varten uusi data class
data class LogoutState(
    val loading: Boolean = false,
    val err: String? = null,
    val logoutOk: Boolean = false
)









// ApiRequestia varten data classit, kirjautumiseen NÄITÄ EI TARVITA. MUISTUTUKSENA TÄÄLLÄ ITELLE.

data class AuthReq(
    val username: String = "",
    val password: String = "")
data class AuthRes(
    @SerializedName("access_token")
    val accessToken: String = ""
)