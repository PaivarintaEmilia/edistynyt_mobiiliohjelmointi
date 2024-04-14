package com.example.edistynyttoinentunti


import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.edistynyttoinentunti.model.CategoryItem
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

// Tehdään alert-ilmoitus, jossa voidaan lisätä uusi categoria
@Composable
fun AddCategoryDialog(addCategory: () -> Unit, name: String, setName: (String) -> Unit, closeDialog: () -> Unit) {

    AlertDialog(
        onDismissRequest = { closeDialog() },
        confirmButton = {
                        TextButton(onClick = { addCategory() }) {
                            Text("Save Category")
                        }
        },
        title = { Text("Add category")},
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = {newName ->
                // Tässä päivitetään uusi arvo stateen, sen mukaan mitä käyttäjä kirjoittaa
                setName(newName) },
                placeholder = { Text("Category name n") })
        })
}




// Tehdään oma composable toiminnolle, jossa kysytään käyttäjältä haluaako tämä poistaa categoryn listalta ALERT-VIESTI
// Muokataan koodia niin, että täällä tapahtuukin poisto
@Composable
fun ConfirmCategoryDelete(onConfirm: () -> Unit, onCancel: () -> Unit, clearErr : () -> Unit,  errString: String?) {

    // Tämä tehdään, koska currentia ei voida käyttää alla olevan launchedEffecting sisällä muuten
    val context = LocalContext.current

    // Launched effect on sama kuin viewModel scope mutta vm:ää ei voi käyttää tässä.
    // Siksi käytetään LaunchedEffectiä. Se siis kuuntelee, muuttuuko muuttuja ja suorittaa..
    // .. sisällä olevan koodin näin tapahtuessa. Tässä tapauksessa näytetään tost-ilmoitus, jos ..
    // .. kategorian poiston yhteydessä tulee virheviesti. Onko errStr null?
    LaunchedEffect(key1 = errString) {

        // Laitetaan ehto sisälle, missä sanotaan, että jos muuttuja ei ole null niin suorita koodi
        // Voitaisiin käyttää if-funktiota myös mutta let toimii sen tilalla | if(errString != null)A
        errString?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            clearErr()
        }
    }


    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {
        TextButton(onClick = { onConfirm() }) {
            Text("Delete")
        }        
    }, dismissButton = {
        // Tällä painikkeella tehdään poiston peruutus
        TextButton(onClick = { onCancel() }) {
            Text("Cancel")
        }
    }, title = {
        Text(text = "Are you sure you want to delete the category?")
    }, text = {
        Text(text = "U sure??")
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit, navigateToEditCategory: (Int) -> Unit, size: WindowSizeInfo) {
    // Tämä on rakennusteline, joka antaa navigaatiolle raamit
    val categoriesVm: CategoriesViewModel = viewModel()



    // Tähän voi itse sitten lisäillä omia osia kuten topBarin
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center, // Keskitetään plus-painike
        // Tässä tulee + -painike, jolla voidaan lisätä categoria
        floatingActionButton = {
                               FloatingActionButton(onClick = { categoriesVm.toggleAddCategory() }) {
                                   Icon(imageVector = Icons.Filled.Add, contentDescription = "Lisää categoria")
                               }
        },

        topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.categories))}, navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        } )
    }) { it ->
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


                // Esitetään alert-ilmoitus, kunvm:ssä toggleAddCategory() funktion muuttujassa tila muuttuu ja näytetään alert AddCategoryDialog()
                categoriesVm.categoriesState.value.isAddingCategory -> AddCategoryDialog(
                    addCategory = {
                                  categoriesVm.createCategory()
                    },
                    name = categoriesVm.addCategoryState.value.name,
                    setName = {newName ->
                        categoriesVm.setNAme(newName)
                    },
                    closeDialog = {
                        categoriesVm.toggleAddCategory()
                    }

                )

                // Tämä tehtiin virheen tarkistusta varten
                categoriesVm.categoriesState.value.err != null -> Text(text = "Tuli virhe: ${categoriesVm.categoriesState.value.err}")

                // Tämä tehdään categorian poiston alert-viestin kutsua varten.
                // Jos id on suurempi kuin 0 niin ollaan clickattu roskis-iconia ja näytetään alert-viesti
                categoriesVm.deleteCategoryState.value.id > 0 -> ConfirmCategoryDelete(onConfirm = {
                    categoriesVm.deleteCategoryById(categoriesVm.deleteCategoryState.value.id)
                }, onCancel = {
                    // Muutetaan tila takaisin 0:ksi, kun käyttäjä ei haluakaan poistaa categoria
                    categoriesVm.verifyCategoryRemoval(0)
                }, clearErr = {
                              // Kutsutaan viewModelin funktiota, jota tarvitaan errorString-muuttujan muuttamiseen nulliksi
                              categoriesVm.clearErr()
                },
                    categoriesVm.deleteCategoryState.value.error) // Näytetään käyttäjälle myös virheviesti. Tulee näkyä alert-dialogissa. Siksi uusi err parametri (löytyy funktiosta)
                    



                // jos ei lataa niin näytetään sisältö näytöllä
                else -> {
                    if(size.widthInfo is WindowType.Compact || size.widthInfo is WindowType.Medium) {
                        CategoriesList(
                            categoriesVm.categoriesState.value.list,
                            verifyCategoryRemoval = {categoryId ->
                            categoriesVm.verifyCategoryRemoval(categoryId)
                        }, navigateToEditCategory)
                    } else {
                        CategoriesMobileOptimization(
                            categories = categoriesVm.categoriesState.value.list,
                            verifyCategoryRemoval = {categoryId ->
                                categoriesVm.verifyCategoryRemoval(categoryId)
                            },
                            navigateToEditCategory = navigateToEditCategory
                        )
                    }
                }
            }
        } // Box loppuu tähän
    }
}


@Composable
fun CategoriesList(categories: List<CategoryItem>, verifyCategoryRemoval : (Int) -> Unit, navigateToEditCategory: (Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        items(categories) {
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
                        IconButton(onClick = { verifyCategoryRemoval(it.id) }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Kategorian poistonpainike")
                        }
                        IconButton(onClick = { navigateToEditCategory(it.id) }) {
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

// Tämä tehtiin mobiilioptimointia varten. 1 per rivi vaihtuu 2 per rivi, kun mennään isommalle näytölle.

@Composable
fun CategoriesMobileOptimization(categories: List<CategoryItem>, verifyCategoryRemoval : (Int) -> Unit, navigateToEditCategory: (Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(categories) {
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
                        IconButton(onClick = { verifyCategoryRemoval(it.id) }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Kategorian poistonpainike")
                        }
                        IconButton(onClick = { navigateToEditCategory(it.id) }) {
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