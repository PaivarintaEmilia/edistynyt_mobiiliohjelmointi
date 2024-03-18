package com.example.edistynyttoinentunti

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen() {

    // kutsutaan viewmodelia
    val vm: CategoryViewModel = viewModel()
    // Scaffouldi
    // Alla luodaan komponentteja ja sisältöä näytölle
    Scaffold (
        topBar = {
            TopAppBar(title = { Text("Entre title here") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to categories")
                    }
                })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                vm.categoryState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                // Virheviestien varalle tehdään tämä
                vm.categoryState.value.err != null -> Text("Virhe ${vm.categoryState.value.err}")
                else -> {
                    // Tässä aletaan rakentamaan käyttöliittymää categorian muokkausta varten
                    // Column stakkaa ylhäältä alaspäin
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Tänne tulee tiedot mitä columnissa on sisällä
                        
                        // Ensin luodaan tekstikenttä
                        OutlinedTextField(
                            value = vm.categoryState.value.item.name, // Categoryn nimi. Mistä tulee?
                            onValueChange = {})
                        
                        // Lisätään tilaa eri komponenttien välille
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Luodaan painike
                        Button(onClick = { /*TODO*/ }) {
                            Text("Edit")
                        }
                        
                    }
                }
            }
        }
    }

}