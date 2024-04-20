package com.example.edistynyttoinentunti.login

import android.util.Log
import androidx.annotation.RestrictTo
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyttoinentunti.api.authService
import com.example.edistynyttoinentunti.model.LogoutState
import kotlinx.coroutines.launch

class logOutViewModel(private val db: AccountDatabase = DbProvider.db) : ViewModel() {

    // Tämä on vain kokeilu, sillä en tiedä mihin laittaa kaikki logout toiminnot

    private val _logoutState = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState


    // Funktio logouOk-muuttujan tilan vaihtoa varten
    fun setLogout(ok: Boolean) {
        _logoutState.value = _logoutState.value.copy(logoutOk = ok)
    }

    // Funktio painikkeen enablointia varten
    fun enableButton(ok: Boolean) {
        _logoutState.value = _logoutState.value.copy(buttonEnable = ok)
    }

    fun changeText() {
        _logoutState.value = _logoutState.value.copy(logOutText = "Sinut on kirjattu ulos.")
    }

    fun logout() {
        viewModelScope.launch {
            try {
                Log.d("emilia", "sisällä logOut funktiossa")
                _logoutState.value = _logoutState.value.copy(loading = true)

                // Requesti. Tarvitsee accessTokenin
                // Saadaan access token DbProviderista
                val accessToken = db.accountDao().getToken()

                // KOODI VIAN TARKISTUKSEEN
                if (accessToken == null) {
                    Log.d("emilia", "No access token available.")
                    _logoutState.value = _logoutState.value.copy(err = "No access token found.")
                    //return@launch
                }

                // VIANETSINTÄ PÄÄTTYY

                Log.d("emilia", "Logging out with token: $accessToken")

                accessToken?.let {
                    authService.logout("Bearer $it")
                    // Tyhjennetään tokenit
                    db.accountDao().removeToken()
                    Log.d("emilia", "ok uloskirjautuminen")
                }

                // Kun logout on ok niin muutetaan logoutOk-muuttujan tilaa
                setLogout(true)

            } catch (e: Exception) {

                _logoutState.value = _logoutState.value.copy(err = e.toString())

            } finally {
                _logoutState.value = _logoutState.value.copy(loading = false)
            }
        }
    }
}