package com.example.edistynyttoinentunti.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.edistynyttoinentunti.model.RegisterReqModel

class RegisterViewModel : ViewModel() {


    // Luodaan statet. Tiedot saadaan register.kt filusta
    private val _registerState = mutableStateOf(RegisterReqModel())
    val registerState: State<RegisterReqModel> = _registerState


}