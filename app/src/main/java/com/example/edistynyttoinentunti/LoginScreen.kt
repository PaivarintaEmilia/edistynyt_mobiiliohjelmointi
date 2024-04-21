package com.example.edistynyttoinentunti

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.viewmodel.LoginViewModel


// Tämä oli aluksi siis MainActivityssa ja siirrettiin navigaation rakentamisen ajaksi tänne.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(goToCategories: () -> Unit, goToRegister: () -> Unit){

    val loginVm: LoginViewModel = viewModel()



    // Virheilmoituksen lisäys tostiin niin näyttää siistimmältä
    // Uudet koodit alkavat tästä
    val context = LocalContext.current

    LaunchedEffect(key1 = loginVm.loginState.value.err) {
        loginVm.loginState.value.err?.let {
            Toast.makeText(context, loginVm.loginState.value.err, Toast.LENGTH_LONG).show()
        }
    }

    // Kuunnellaan logOk-muuttujaa. Jos kirjautuminen on ok niin navigoidaan categories-screeniin.

    LaunchedEffect(key1 = loginVm.loginState.value.logOk) {
        if (loginVm.loginState.value.logOk) {
            // Asetetaan muuttuja takaisin falseksi
            loginVm.setLogin(false)
            // Tilasiirtymä
            goToCategories()
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.logIn)) })
    }) {

        // Box (pinotaan z-akselilla) käytetään, sillä loading iconi oli hirveän iso.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Näyttää loading iconia. Eli jos loading on true -> näytetään iconia. Muuten näytetään login screeni. (when -> else)
            when {
                loginVm.loginState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                loginVm.loginState.value.err != null -> {
                    Text("Error: ${loginVm.loginState.value.err}")
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
                            Text(text = stringResource(R.string.usernameHint))
                        }) //tämä on tekstikenttä, johon syötetään käyttäjätunnus
                    Spacer(modifier = Modifier.height(25.dp)) // Saadaan väliä
                    OutlinedTextField(
                        value = loginVm.loginState.value.password,
                        onValueChange = { newPassword ->
                            loginVm.setPassword(newPassword) // Tällä haetaan funktio loginViewModel.kt tiedostosta
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.passwordHint))
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Saadaan väliä
                    // Lisätään Login-painike.
                    // Enabled. Määrittää ehdon, että jos username ja password on tyhjä niin nappia ei voida painaa.
                    Button(enabled = loginVm.loginState.value.username != "" && loginVm.loginState.value.password != "",
                        onClick = {
                            loginVm.login()
                            //goToCategories() // Navigoi meidät categories sceereeen. Composablessa kerrotaan mitä tässä oikeasti tapahtuu.
                        }) {
                        Text(text = stringResource(R.string.logIn))
                    }

                    Button(
                        onClick = {
                            goToRegister()
                        }) {
                        Text(text = stringResource(R.string.register))
                    }
                }
            }
        }
    }
}
