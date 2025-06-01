package com.example.swakopmundapp.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.components.AnnotatedClickableText
import com.example.swakopmundapp.ui.components.ButtonComponent
import com.example.swakopmundapp.ui.components.ClickablePart
import com.example.swakopmundapp.ui.components.HeadingTextComponent
import com.example.swakopmundapp.ui.components.MyTextFieldComponent
import com.example.swakopmundapp.ui.components.NormalTextComponent
import com.example.swakopmundapp.ui.components.PasswordTextFieldComponent
import com.example.swakopmundapp.ui.components.ResidencyDropdownField
import com.example.swakopmundapp.ui.components.ResidencyStatus
import com.example.swakopmundapp.ui.components.WhiteSheet
import com.example.swakopmundapp.ui.navigation.Screen


@Composable
fun SignUpScreen(navController: NavController) {
    var residency by remember { mutableStateOf<ResidencyStatus?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.swakop_sc_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                        .padding(horizontal = 16.dp), // Optional: better spacing inside
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextComponent(value = stringResource(id = R.string.signup))
                    NormalTextComponent(value = stringResource(id = R.string.create_account))

                    MyTextFieldComponent(labelValue = stringResource(id = R.string.first_name))
                    MyTextFieldComponent(labelValue = stringResource(id = R.string.last_name))
                    MyTextFieldComponent(labelValue = stringResource(id = R.string.email))
                    MyTextFieldComponent(labelValue = stringResource(id = R.string.phone_number))
                    PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password))
                    ResidencyDropdownField(
                        selectedStatus = residency,
                        onStatusSelected = { residency = it },
                        modifier = Modifier.fillMaxWidth(0.92f)
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    ButtonComponent(
                        value = stringResource(id = R.string.signup)
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
    }
}


@Preview
@Composable
fun SignUpPreview() {
    SignUpScreen(navController = rememberNavController())
}