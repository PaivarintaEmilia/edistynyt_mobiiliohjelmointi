package com.example.edistynyttoinentunti.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.edistynyttoinentunti.model.LoginReqModel

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf(LoginReqModel())
    val loginState: State<LoginReqModel> = _loginState
}