package com.viridis.ui.auth

import androidx.lifecycle.ViewModel
import com.viridis.data.models.SignInModel
import com.viridis.data.models.SignInStateModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInStateModel())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInModel) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { SignInStateModel() }
    }
}