package com.example.edistynyttoinentunti.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.authService
import com.example.edistynyttoinentunti.model.RegisterReqModel
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {


    // Luodaan statet. Tiedot saadaan register.kt filusta
    private val _registerState = mutableStateOf(RegisterReqModel())
    val registerState: State<RegisterReqModel> = _registerState

    // Funktio, jotta tekstikentässä teksti näkyy
    fun setTextUsername(username: String) {
        _registerState.value = _registerState.value.copy(username = username)
    }

    // Funktio, jotta tekstikentässä teksti näkyy
    fun setTextPassword(password: String) {
        _registerState.value = _registerState.value.copy(password = password)
    }

    // FUnktio, jolla tarkastetaan, että rekisteröinti on onnistunut
    fun setRegister(ok: Boolean) {
        _registerState.value = _registerState.value.copy(registerOk = ok)
    }

    // Tähän perus viewModelin toiminnot
    fun register() {

        viewModelScope.launch {
            try {

                Log.d("Emiliavm", _registerState.value.registerOk.toString())

                // Sen aikaa, kun lataa niin näytetään screenin puolella animaatiota
                _registerState.value = _registerState.value.copy(loading = true)

                // Kutsu ja asetetaan arvot muuttujiin
                val res = authService.register(
                    RegisterReqModel(
                        username = _registerState.value.username,
                        password = _registerState.value.password
                    )
                )

                // Tarkistetaan että kutsu toimii ja toimiihan se!
                Log.d("Emilia", res.authUserId.toString())

                // Asetetaan muuttuja trueksi, kun kutsu on onnistunut
                setRegister(true)

                Log.d("Emiliavm2", _registerState.value.registerOk.toString())

            } catch (e: Exception) {

                _registerState.value = _registerState.value.copy(err = e.toString())

            } finally {

                _registerState.value = _registerState.value.copy(loading = false)

            }

        }
    }

}