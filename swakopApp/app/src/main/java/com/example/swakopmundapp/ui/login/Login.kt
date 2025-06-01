package com.example.swakopmundapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.repository.transaction.TransactionHandler
import com.example.swakopmundapp.ui.components.AnnotatedClickableText
import com.example.swakopmundapp.ui.components.ClickablePart
import com.example.swakopmundapp.ui.components.HeadingTextComponent
import com.example.swakopmundapp.ui.components.NormalTextComponent
import com.example.swakopmundapp.ui.components.WhiteSheet
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.viewModel.AuthenticationViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import com.example.swakopmundapp.ui.components.LoginTextFieldComponent
import com.example.swakopmundapp.ui.components.PasswordTextFieldComponent

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf<LiveData<TransactionHandler<out Any?>>?>(null) }

    loginResult?.observeAsState()?.value?.let { result ->
        when (result) {
            is TransactionHandler.Started -> {
                isLoading = true
            }
            is TransactionHandler.SuccessfullyCompleted -> {
                isLoading = false
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }

                email = ""
                password = ""
                loginResult = null
            }
            is TransactionHandler.PoorConnection -> {
                isLoading = false
                errorMessage = "Slow internet connection"
            }
            is TransactionHandler.Error -> {
                isLoading = false
                errorMessage = result.error?.localizedMessage ?: "Something went wrong"
            }
            else -> {
                isLoading = false
                errorMessage = (result.data ?: "Something went wrong") as? String ?: "Error"
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.swakop_sc_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (logo, sheet) = createRefs()

            // Logo
            Image(
                painter = painterResource(id = R.drawable.swakop_logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .constrainAs(logo) {
                        top.linkTo(parent.top, margin = 60.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(16.dp)
            )

            // White Bottom Sheet
            WhiteSheet(
                modifier = Modifier
                    .constrainAs(sheet) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .alpha(0.85f),
                cornerRadius = 24.dp
            ) {
                HeadingTextComponent(value = stringResource(id = R.string.login))
                NormalTextComponent(value = stringResource(id = R.string.welcome))

                // Email Field
                LoginTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    }
                )

                // Password Field
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Log In Button
                Button(
                    onClick = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            isLoading = true
                            errorMessage = ""

                            val loginDto = LoginDto(
                                username = email,
                                password = password
                            )

                            loginResult = viewModel.login(loginDto)
                        }
                    },
                    enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    if (isLoading) {
                        Text(text = "Logging in...")
                    } else {
                        Text(text = stringResource(id = R.string.log_in))
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sign Up Text
                AnnotatedClickableText(
                    parts = listOf(
                        ClickablePart("Don't have an account? "),
                        ClickablePart(text = "Sign Up", tag = "SIGNUP")
                    ),
                    defaultStyle = TextStyle(fontSize = 14.sp)
                ) { tag ->
                    if (tag == "SIGNUP") {
                        navController.navigate(Screen.SignUp.route)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
