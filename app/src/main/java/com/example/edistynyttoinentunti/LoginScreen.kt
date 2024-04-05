package com.example.edistynyttoinentunti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.viewmodel.LoginViewModel


// Tämä oli aluksi siis MainActivityssa ja siirrettiin navigaation rakentamisen ajaksi tänne.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(goToCategories: () -> Unit){

    val loginVm: LoginViewModel = viewModel()

    Scaffold(topBar = {
        TopAppBar(title = { Text("LogIn") })
    }) {

        // Box (pinotaan z-akselilla) käytetään, sillä loading iconi oli hirveän iso.
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            // Näyttää loading iconia. Eli jos loading on true -> näytetään iconia. Muuten näytetään login screeni. (when -> else)
            when {
                loginVm.loginState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                loginVm.loginState.value.err != null -> {
                    Text("Virhe: ${loginVm.loginState.value.err}")
                }


                else -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = loginVm.loginState.value.username,
                        onValueChange = { newUsername ->
                            loginVm.setUserName(newUsername) // Tällä haetaan funktio loginViewModel.kt tiedostosta
                        },
                        placeholder = {
                            Text(text = "Username_hint")
                        }) //tämä on tekstikenttä, johon syötetään käyttäjätunnus
                    Spacer(modifier = Modifier.height(16.dp)) // Saadaan väliä
                    OutlinedTextField(
                        value = loginVm.loginState.value.password,
                        onValueChange = { newPassword ->
                            loginVm.setPassword(newPassword) // Tällä haetaan funktio loginViewModel.kt tiedostosta
                        },
                        placeholder = {
                            Text(text = "Password_hint")
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Saadaan väliä
                    // Lisätään Login-painike.
                    // Enabled. Määrittää ehdon, että jos username ja password on tyhjä niin nappia ei voida painaa.
                    Button(enabled = loginVm.loginState.value.username != "" && loginVm.loginState.value.password != "",
                        onClick = {
                            loginVm.login()
                            goToCategories() // Navigoi meidät categories sceereeen. Composablessa kerrotaan mitä tässä oikeasti tapahtuu.
                        }) {
                        Text(text = "Login")
                    }
                }
            }
        }
    }
}
