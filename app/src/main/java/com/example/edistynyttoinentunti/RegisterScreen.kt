package com.example.edistynyttoinentunti

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.edistynyttoinentunti.viewmodel.RegisterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(goToCategories: () -> Unit, backToLogin: () -> Unit){

    val registerVm: RegisterViewModel = viewModel()

    Log.d("Emilia", registerVm.registerState.value.registerOk.toString())

    // LaunchedEffect, joka seuraa onko kutsu onnistunut ja muuttaa muuttujan takaisin
    // falseksi ja siirtyy categories-listaukseen, jos kaikki on ok.
    LaunchedEffect(key1 = registerVm.registerState.value.registerOk) {
        registerVm.setRegister(false)
        //goToCategories() Tarkoitus on jättää tämä tähän mutta jos tämä on tässä niin Register-painikkeesta
        // loginScreen-filusta ohjautuu suoraan cateogries-listaukseen? En saanut vielä selvitettyä
    }

    Log.d("Emilia2", registerVm.registerState.value.registerOk.toString())


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    IconButton(onClick = { backToLogin() }) {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = "Go back to Login Screen")
                    }
                })
        }

    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it) ) {

            when {

                registerVm.registerState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                registerVm.registerState.value.err != null -> {
                    Text("Virhe: ${registerVm.registerState.value.err}")
                }

                else -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    OutlinedTextField(
                        value = registerVm.registerState.value.username,
                        onValueChange = {
                            registerVm.setTextUsername(it)
                        },
                        placeholder = {
                            Text(text = "New username")
                        })

                    Spacer(modifier = Modifier.height(25.dp))

                    OutlinedTextField(
                        value = registerVm.registerState.value.password,
                        onValueChange = {
                             registerVm.setTextPassword(it) // Saadaan tekstikenttään tekstiä
                        },
                        placeholder = {
                            Text(text = "New password")
                        },
                        visualTransformation = PasswordVisualTransformation()

                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        enabled = registerVm.registerState.value.username != "" && registerVm.registerState.value.password != "",
                        onClick = {
                            registerVm.register()
                            goToCategories()
                        }) {

                        Text(text = "Register new user")
                    }
                }
            }
        }
    }
}