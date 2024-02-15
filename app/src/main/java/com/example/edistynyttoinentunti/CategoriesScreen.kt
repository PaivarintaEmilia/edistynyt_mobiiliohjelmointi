package com.example.edistynyttoinentunti


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.viewmodel.CategoriesViewModel

//Näin saadaan preview näkymä käyttöön, muuten ei tarpeellinen
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CategoriesScreenPreview() {

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Categories") }, navigationIcon = {
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        } )
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {}
    }
}
// Tämä tehtiin vain, jotta voidaan tarkastella navigaatiota

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit) {
    // Tämä on rakennusteline, joka antaa navigaatiolle raamit
    val categoriesVm: CategoriesViewModel = viewModel()
    // Tähän voi itse sitten lisäillä omia osia kuten topBarin
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Categories") }, navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        } )
    }) {
    // Tähän tulee saada itemit CategoriesStatesta, joka sijaitsee CategoriesViewModelissa.
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            // Boxiin voidaan asetaa asioita z-akselin
            when {
                categoriesVm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                    Alignment.Center
                ))
                else -> LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    items(categoriesVm.categoriesState.value.list) {
                        // Yksikkäinen itemi mikä näytetään näytöllä
                        // Tämä on itemin scope
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(Modifier.fillMaxWidth().padding(15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Kuva tähän myöhemmin")
                                Row(horizontalArrangement = Arrangement.End) {
                                    Text(text = it.name, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        } // Box loppuu tähän


    }
}