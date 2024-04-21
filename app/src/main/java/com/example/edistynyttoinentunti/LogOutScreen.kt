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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyttoinentunti.login.logOutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogOutScreen( goToLogin: () -> Unit, backToCategories: () -> Unit){

    val logOutVm: logOutViewModel = viewModel()

    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.logOut)) }, // Tämä on yläbannerin title, joka saadaan db:stä.
                navigationIcon = {
                    IconButton(onClick = { backToCategories() }) {
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
                logOutVm.logoutState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                logOutVm.logoutState.value.err != null -> Text("Error: ${logOutVm.logoutState.value.err}")

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {



                        Text(text = "${logOutVm.logoutState.value.logOutText}")

                        Spacer(modifier = Modifier.height(25.dp))


                        Button(
                            enabled = !logOutVm.logoutState.value.buttonEnable,
                            onClick = {
                            logOutVm.logout();
                            logOutVm.changeText();
                            logOutVm.enableButton(true)
                        }) {
                            Text(text = stringResource(R.string.confirmLogOut))
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Button(
                            // Kun muuttuja on false niin button on enabladed
                            enabled = logOutVm.logoutState.value.buttonEnable,
                            onClick = { goToLogin(); logOutVm.enableButton(false) }) {
                            Text(text = stringResource(R.string.logBackIn))
                        }

                    }
                }
            }
        }
    }
}


