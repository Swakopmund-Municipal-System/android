package com.example.swakopmundapp.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.repository.transaction.TransactionHandler
import com.example.swakopmundapp.ui.components.AnnotatedClickableText
import com.example.swakopmundapp.ui.components.ButtonComponent
import com.example.swakopmundapp.ui.components.ClickablePart
import com.example.swakopmundapp.ui.components.HeadingTextComponent
import com.example.swakopmundapp.ui.components.NormalTextComponent
import com.example.swakopmundapp.ui.components.PasswordTextFieldComponent
import com.example.swakopmundapp.ui.components.ResidencyDropdownField
import com.example.swakopmundapp.ui.components.UserTypes
import com.example.swakopmundapp.ui.components.WhiteSheet
import com.example.swakopmundapp.ui.navigation.Screen
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.ui.components.MyTextFieldComponent2
import com.example.swakopmundapp.util.PREF_ROLE
import com.example.swakopmundapp.viewModel.AuthenticationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    var residency by remember { mutableStateOf<UserTypes?>(null) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var signupResult by remember { mutableStateOf<LiveData<TransactionHandler<out Any?>>?>(null) }

    signupResult?.observeAsState()?.value?.let { result ->

        when (result) {

            is TransactionHandler.Started -> {
                isLoading = true
                errorMessage = ""
            }

            is TransactionHandler.SuccessfullyCompleted -> {
                isLoading = false

                // saved user role in prefs
                PREF_ROLE = when (residency) {
                    UserTypes.Resident -> "resident"
                    UserTypes.Tourist -> "tourist"
                    else -> ""
                }

                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }

                firstName = ""
                lastName = ""
                email = ""
                password = ""
                address = ""
                residency = null
                signupResult = null
            }

            is TransactionHandler.PoorConnection -> {
                isLoading = false
                errorMessage = "Poor internet connection. Please try again."
            }

            is TransactionHandler.Error -> {
                isLoading = false
                errorMessage = result.error?.localizedMessage ?: "Registration failed. Please try again."
            }

            else -> {
                isLoading = false
                errorMessage = "Something went wrong. Please try again."
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.swakop_sc_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.swakop_logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .padding(28.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))

            WhiteSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .alpha(0.85f),
                cornerRadius = 24.dp
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextComponent(value = stringResource(id = R.string.signup))
                    NormalTextComponent(value = stringResource(id = R.string.create_account))

                    MyTextFieldComponent2(
                        labelValue = stringResource(id = R.string.first_name),
                        value = firstName,
                        onValueChange = { firstName = it }
                    )

                    MyTextFieldComponent2(
                        labelValue = stringResource(id = R.string.last_name),
                        value = lastName,
                        onValueChange = { lastName = it }
                    )

                    MyTextFieldComponent2(
                        labelValue = stringResource(id = R.string.email),
                        value = email,
                        onValueChange = { email = it }
                    )

                    PasswordTextFieldComponent(
                        labelValue = stringResource(id = R.string.password),
                        value = password,
                        onValueChange = { password = it }
                    )

                    ResidencyDropdownField(
                        selectedStatus = residency,
                        onStatusSelected = {
                            residency = it

                            if (it == UserTypes.Tourist) {
                                address = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.92f)
                    )

                    if (residency == UserTypes.Resident) {
                        Spacer(modifier = Modifier.height(8.dp))
                        MyTextFieldComponent2(
                            labelValue = "Home Address",
                            value = address,
                            onValueChange = { address = it }
                        )
                    }

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    ButtonComponent(
                        value = if (isLoading) "Creating Account..." else stringResource(id = R.string.signup),
                        enabled = true,
                        onClick = {
                            if (!isLoading) {

                                val signUpDto = when (residency) {
                                    UserTypes.Resident -> SignUpDto(
                                        email = email,              // ✅ 1st parameter
                                        password = password,               // ✅ 2nd parameter
                                        firstName = firstName,      // ✅ 3rd parameter
                                        lastName = lastName,
                                        homeAddress = address,
                                        userType = listOf("resident")
                                    )
                                    UserTypes.Tourist -> SignUpDto(
                                        email = email,              // ✅ 1st parameter
                                        password = password,               // ✅ 2nd parameter
                                        firstName = firstName,      // ✅ 3rd parameter
                                        lastName = lastName,
                                        homeAddress = null,
                                        userType = listOf("tourist")
                                    )
                                    else -> null
                                }

                                signUpDto?.let {
                                    signupResult = viewModel.signup(it)
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AnnotatedClickableText(
                        parts = listOf(
                            ClickablePart("Already have an account? "),
                            ClickablePart(text = "Login", tag = "LOGIN")
                        ),
                        defaultStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
                    ) { tag ->
                        if (tag == "LOGIN") {
                            navController.navigate(Screen.Login.route)
                        }
                    }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
    }
}

@Preview
@Composable
fun SignUpPreview() {
    SignUpScreen(navController = rememberNavController())
}