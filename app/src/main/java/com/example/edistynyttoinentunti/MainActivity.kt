package com.example.edistynyttoinentunti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.ui.theme.EdistynytToinenTuntiTheme
import com.example.edistynyttoinentunti.viewmodel.LoginViewModel

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
                    //LoginScreen() // Näin saadaan luotu composable näytölle (Piilotettiin navigaation teon alussa. Ehkä väliaikaisesti)
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
                    val scope = rememberCoroutineScope() // Hampurilaisvalikon avaamista ja sulkemista varten tarvitaan scope (liittyi asyncronosiin funkitoihin)
                    ModalNavigationDrawer(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Categories_drawer") },
                                    selected = true,
                                    onClick = { /*TODO*/ }, icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "Home_Drawer"  // Tämä on tärkeä saatavuuden kannalta
                                        )
                                    })
                            }
                        }, drawerState = drawerState) {
                        Text("Welcome home") // Tämä vain lisättiin navigaation rakennuksen yhteydessä -> tulee esille ruutuun
                    }
                }
            }
        }
    }
}

// Columnissa voidaan keskittää kaikki sisällä olevat elementit seuraavilla parametreilla
// Alt + Enter tekee importin



