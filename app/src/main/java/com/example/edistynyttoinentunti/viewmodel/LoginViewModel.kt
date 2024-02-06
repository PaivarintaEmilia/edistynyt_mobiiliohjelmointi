package com.example.edistynyttoinentunti.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.edistynyttoinentunti.model.LoginReqModel

class LoginViewModel : ViewModel() {
    // Tätä voidaan muuttaa
    private val _loginState = mutableStateOf(LoginReqModel())
    // Tätä ei voida muuttaa
    val loginState: State<LoginReqModel> = _loginState

    // funktio joka asettaa uuden usernamen. Argumenttina käytetään uutta käyttäjätunnusta.
    fun setUserName(newUsername: String) {
        // Copyn avulla päivitetään käyttöliittymä
        _loginState.value = _loginState.value.copy(username = newUsername)
    }
}