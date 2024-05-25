package com.viridis

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.viridis.data.models.SignInStateModel
import com.viridis.ui.auth.SignInScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun signInScreenTest() {
        composeTestRule.setContent {
            val signInState = SignInStateModel(signInError = null)
            SignInScreen(
                state = signInState,
                isLoadingState = false,
                onSignInClick = {}
            )
        }

        composeTestRule.onNodeWithText("Prijavite se").assertIsDisplayed()
    }
}