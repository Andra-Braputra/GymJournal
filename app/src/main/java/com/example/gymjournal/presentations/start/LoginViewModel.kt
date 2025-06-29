package com.example.gymjournal.presentations.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.core.Response
import com.example.gymjournal.domain.usecase.auth.LoginUseCase
import com.example.gymjournal.domain.usecase.auth.ResetPasswordUseCase
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _loginFlow = MutableSharedFlow<Response<AuthResult>>()
    val loginFlow = _loginFlow

    private val _resetPasswordFlow = MutableSharedFlow<Response<Void?>>()
    val resetPasswordFlow = _resetPasswordFlow

    fun login(email: String, password: String) = viewModelScope.launch {
        loginUseCase.invoke(email, password).collect { response ->
            _loginFlow.emit(response)
        }
    }

    fun resetPassword(email: String) = viewModelScope.launch {
        resetPasswordUseCase.invoke(email).collect { response ->
            _resetPasswordFlow.emit(response)
        }
    }

}