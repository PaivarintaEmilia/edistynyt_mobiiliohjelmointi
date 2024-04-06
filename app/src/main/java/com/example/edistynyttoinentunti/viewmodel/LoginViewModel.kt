package com.example.edistynyttoinentunti.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.authService
import com.example.edistynyttoinentunti.model.LoginReqModel
import com.example.edistynyttoinentunti.model.LoginResModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch




class LoginViewModel : ViewModel() {
    // Tätä voidaan muuttaa
    private val _loginState = mutableStateOf(LoginReqModel())
    // Tätä ei voida muuttaa
    val loginState: State<LoginReqModel> = _loginState

    // funktio joka asettaa uuden usernamen. Argumenttina käytetään uutta käyttäjätunnusta. Kutsutaan OutlinedTextFieldissä screenissä.
    fun setUserName(newUsername: String) {
        // Copyn avulla päivitetään käyttöliittymä
        _loginState.value = _loginState.value.copy(username = newUsername)
    }

    // Tehdään saman tyyppinen funktio mutta passwordill. Kutsutaan OutlinedTextFieldissä screenissä.

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

        viewModelScope.launch {
            try {

                _loginState.value = _loginState.value.copy(loading = true)

                //viewModelScope on valmiiksi tullut viewmodelista. Sitä tarvitaan, että voidaan käyttää suspendia normaalin funktion sisällä.
                _waitForLogin()
                // Tähän tulee login.kt:ssä luodut loginResModelin asiat. Mikä tämä on?
                val user = LoginResModel()

                // Kutsu
                val res = authService.login(
                    LoginReqModel(
                        username = _loginState.value.username,
                        password = _loginState.value.password
                    )
                )
                Log.d("Emilia", res.accessToken) // Toimii


            } catch (e: Exception) {

                // Virheviestit
                _loginState.value = _loginState.value.copy(err = e.toString())

            } finally {
                // Loadingista tulee taas false. Tällä demontroidaan sitä, että oltaisiin kirjauduttu sovellukseen sisään.
                _loginState.value = _loginState.value.copy(loading = false)

            }



        }
    }

}