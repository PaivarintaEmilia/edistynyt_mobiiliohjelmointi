package com.example.edistynyttoinentunti


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        LazyColumn(modifier = Modifier.padding(it)) {
            // Tähän tulee saada itemit CategoriesStatesta, joka sijaitsee CategoriesViewModelissa.

        }
        // Tämä tehtiin vain, jotta saadaan virheilmoitus pois
    }
}