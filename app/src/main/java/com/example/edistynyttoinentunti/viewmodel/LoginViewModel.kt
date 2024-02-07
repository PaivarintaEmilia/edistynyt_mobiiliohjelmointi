package com.example.edistynyttoinentunti.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.model.LoginReqModel
import com.example.edistynyttoinentunti.model.LoginResModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    // Tehdään saman tyyppinen funktio mutta passwordille

    fun setPassword(newPassword: String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }


    // Tehdään keinotekoinen delay, jotta nähdään paremmin mitä lautauksessa tapahtuu. Muuten ei kerettäisi näkemään tätä.
    // Suspend on sama kuin async
    private suspend fun _waitForLogin() {
        delay(2000)
    }

    // Loading on aluksi false. Tässä funktiossa se muutetaan loginstaten loading
    fun login() {
        _loginState.value = _loginState.value.copy(loading = true)
        viewModelScope.launch {
            //viewModelScope on valmiiksi tullut viewmodelista. Sitä tarvitaan, että voidaan käyttää suspendia normaalin funktion sisällä.
            _waitForLogin()
            // Tähän tulee login.kt:ssä luodut loginResModelin asiat
            val user = LoginResModel()
            // Loadingista tulee taas false. Tällä demontroidaan sitä, että oltaisiin kirjauduttu sovellukseen sisään.
            _loginState.value = _loginState.value.copy(loading = false)
        }
    }

}