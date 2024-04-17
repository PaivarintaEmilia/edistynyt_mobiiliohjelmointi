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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edistynyttoinentunti.login.logOutViewModel
import com.example.edistynyttoinentunti.ui.theme.EdistynytToinenTuntiTheme
import com.example.edistynyttoinentunti.viewmodel.LoginViewModel
import kotlinx.coroutines.launch




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdistynytToinenTuntiTheme {
                // Mobiilioptimointi
                val windowSizeInfo = rememberWindowSize()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val vm: logOutViewModel = viewModel() // Logouttia varten yhdistettiin tänne. Tämä kokeilu
                    //LoginScreen() // Näin saadaan luotu composable näytölle (Piilotettiin navigaation teon alussa. Ehkä väliaikaisesti)
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Tämä on suspend functio, koska tässä on animaatio. Suorittaa animaation ja sitten avautuu tai sulkeutuu.
                    val scope = rememberCoroutineScope() // Hampurilaisvalikon avaamista ja sulkemista varten tarvitaan scope (liittyi asyncronosiin funkitoihin)
                    val navController = rememberNavController() // Mikä tämä on?
                    ModalNavigationDrawer(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Categories_drawer") },
                                    selected = true,
                                    onClick = { scope.launch { drawerState.close() } }, icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "Home_Drawer"  // Tämä on tärkeä saatavuuden kannalta
                                        )
                                    })
                                NavigationDrawerItem(
                                    label = { Text(text = "Login_drawer") },
                                    selected = false,
                                    onClick = { scope.launch {
                                        navController.navigate(Screen.Login.route)
                                        drawerState.close() } },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Lock,
                                            contentDescription = "Login_Drawer"  // Tämä on tärkeä saatavuuden kannalta
                                        )
                                    })
                                NavigationDrawerItem(
                                    label = { Text(text = "My_own_drawer") },
                                    selected = false,
                                    onClick = { scope.launch {
                                        navController.navigate(Screen.Login.route)
                                        drawerState.close() } },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.ShoppingCart,
                                            contentDescription = "My_own_Drawer"  // Tämä on tärkeä saatavuuden kannalta
                                        )
                                    })
                                NavigationDrawerItem(
                                    label = { Text(text = "Log_out_drawer") },
                                    selected = false,
                                    onClick = { scope.launch {
                                        vm.logout()
                                        if (vm.logoutState.value.logoutOk) {
                                            vm.setLogout(false)
                                            navController.navigate(Screen.Login.route) {
                                                popUpTo(Screen.Login.route) {
                                                    inclusive = true
                                                }
                                            }
                                        }

                                        drawerState.close() } },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.ExitToApp,
                                            contentDescription = "Logout_Drawer"  // Tämä on tärkeä
                                        // saatavuuden kannalta
                                        )
                                    })
                            }
                        }, drawerState = drawerState) {
                        NavHost(navController = navController, startDestination = Screen.Login.route) {
                        // Kaikki elementit joihin pitää pystyä navigoimaan

                            // Tässä tehdään uusi composable api kutsun harjoitusta varten. Luodaan tähän, jotta ei tarvitse luoda nappia.
                            composable(route= Screen.PostsScreen.route) {
                                PostsScreen()
                            }

                           composable(route = Screen.Categories.route) {
                                CategoriesScreen(onMenuClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }, navigateToEditCategory = {
                                    navController.navigate("${Screen.EditCategoryScreen.route}/${it}") // Tämä lisättiin, jotta päästään category screenistä editcategoryscreeniin
                                }, size = windowSizeInfo) // Ei sulkuja koska ei haluta kutsua vaan lähetetään vain eteenpäin composable puussa
                            }

                           composable(Screen.Login.route) {
                               LoginScreen(goToCategories = {
                                   navController.navigate(Screen.Categories.route) // CallBack löytyy loginScreen tiedostosta
                               }, goToRegister = {
                                   navController.navigate(Screen.RegisterScreen.route)
                               })
                            }
                            composable("editCategoryScreen/{category}") {
                                EditCategoryScreen(backToCategories = {
                                    navController.navigateUp() // Navigoi automaattisesti takaisin aikaisempaan screeniin
                                }, goToCategories = {
                                    navController.navigate(Screen.Categories.route)
                                })
                            }

                            // composable Registerylle. Siitä navigoidaan categories listaukseen ja loginiin.
                            composable(Screen.RegisterScreen.route) {
                                RegisterScreen(goToCategories = {
                                    navController.navigate(Screen.Categories.route)
                                }, backToLogin = {
                                    navController.navigate(Screen.Login.route)
                                })
                            }




                        }
                        /*AppNavigation(onMenuClick =  {
                            // Tähän tulee menu iconin painikkeen painamisen toiminto, koska drawerState on täällä (CategoriesScreen)
                            // NavigatorDrawerin state on täällä
                            // Eli lisätään scopen sisään napin fukntiot.
                            scope.launch {
                                if(drawerState.currentValue == DrawerValue.Closed) {
                                    drawerState.open() // Voi olla että jätetään vaan tämä koska nappia ei näy
                                } else {
                                    drawerState.close()
                                }
                            } // Tämä kaikki kommentoitu, koska opesta tämä oli sekavaa.
                        }) */
                    }
                }
            }
        }
    }
}

// TARKASTELE TÄTÄ VAIKKA POISTETTIINKIN
// Tässä aletaan rakentamaan navigaatiota
//@Composable
//fun AppNavigation(onMenuClick: () -> Unit) {
//    val navController = rememberNavController()
    //NavHost(navController = navController, startDestination = "CategoriesScreen") {
        // Kaikki elementit joihin pitää pystyä navigoimaan
    //    composable(route = "CategoriesScreen") {
    //        CategoriesScreen(onMenuClick) // Ei sulkuja koska ei haluta kutsua vaan lähetetään vain eteenpäin composable puussa
    //    }

    //} Otetaan tämä osa pois koska opettaja sanoi, että tämä sekoittaa kun meennään usean composablen läpi
//}



