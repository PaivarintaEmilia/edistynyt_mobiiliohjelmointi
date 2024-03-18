package com.example.edistynyttoinentunti


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.edistynyttoinentunti.viewmodel.CategoriesViewModel

// Haetaan kuva lorem picsumista. Tehtiin tälle oma composable.
@Composable
fun RandomImage() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https:/picsum.photos/300")
            .build(),
        contentDescription = "random image"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit, navigateToEditCategoty: (Int) -> Unit) {
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
                // jos ladataan niin näytetään latauskuvaketta
                categoriesVm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                    Alignment.Center
                    )
                )

                // Tämä tehtiin virheen tarkistusta varten
                categoriesVm.categoriesState.value.err != null -> Text(text = "Tuli virhe: ${categoriesVm.categoriesState.value.err}")


                // jos ei lataa niin näytetään sisältö näytöllä
                else -> LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    items(categoriesVm.categoriesState.value.list) {
                        // Yksikkäinen itemi mikä näytetään näytöllä
                        // Tämä on itemin scope
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                // Kutsutaan random image composablea, jotta saadaan kuva näkyviin.
                                RandomImage()
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = it.name,
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.headlineSmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Delete,
                                            contentDescription = "Kategorian poistonpainike")
                                    }
                                    IconButton(onClick = { navigateToEditCategoty(it.id) }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Edit,
                                            contentDescription = "Kategorian editointipainike")
                                        // Tästä päästään editCategorySceeniin
                                        // Lisäksi lisättiin nav hostiin mainActivityyn tämä
                                        // it.id on jotta tiedetään mitä category itemiä klikataan!
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } // Box loppuu tähän
    }
}