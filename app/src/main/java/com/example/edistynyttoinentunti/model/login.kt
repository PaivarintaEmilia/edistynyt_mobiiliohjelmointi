package com.example.edistynyttoinentunti.model


// Login sivun alku muuttujat
data class LoginReqModel(
    val err: String? = null,
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false
)

// Vastaus, kun login-tiedot on syötetty ja painetaan login-painiketta

data class LoginResModel(
    val id: Int = 0, // Tämä on responsessa luotu id käyttäjälle. Oletusarvo on 0.
    val accessToken: String = "", //Tämä tulee aina loginin yhteydessä. Vielä ei tarvitse tietää mistä on kyse.
    val username: String = "" // Tämän avulla voidaan luoda ilmoitus "Hei KÄyttäjä + username!"

    )


