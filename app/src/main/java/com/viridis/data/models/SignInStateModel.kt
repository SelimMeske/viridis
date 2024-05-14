package com.viridis.data.models

data class SignInStateModel(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)