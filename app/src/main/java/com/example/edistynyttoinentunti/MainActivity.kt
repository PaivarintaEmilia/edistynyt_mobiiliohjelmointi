package com.example.edistynyttoinentunti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edistynyttoinentunti.ui.theme.EdistynytToinenTuntiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdistynytToinenTuntiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen() // Näin saadaan luotu composable näytölle
                }
            }
        }
    }
}

// Columnissa voidaan keskittää kaikki sisällä olevat elementit seuraavilla parametreilla
// Alt + Enter tekee importin
// harjoitus

@Composable
fun LoginScreen(){
    val username = remember{
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
        OutlinedTextField(value = username.value, onValueChange = { newUsername ->
                username.value = newUsername
        }, placeholder = {
            Text(text = "Username")
        }) //tämä on tekstikenttä, johon syötetään käyttäjätunnus
        Spacer(modifier = Modifier.height(16.dp)) // Saadaan väliä
        OutlinedTextField(value = password.value, onValueChange = {
                                                                  newPassword ->
                                                                  password.value = newPassword
        }, placeholder = {
            Text(text = "Password")
                         })
    }
}

