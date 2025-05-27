package com.example.swakopmundapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
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
import com.example.swakopmundapp.ui.components.AnnotatedClickableText
import com.example.swakopmundapp.ui.components.ClickablePart
import com.example.swakopmundapp.ui.components.ButtonComponent
import com.example.swakopmundapp.ui.components.HeadingTextComponent
import com.example.swakopmundapp.ui.components.MyTextFieldComponent
import com.example.swakopmundapp.ui.components.NormalTextComponent
import com.example.swakopmundapp.ui.components.PasswordTextFieldComponent
import com.example.swakopmundapp.ui.components.WhiteSheet
import com.example.swakopmundapp.ui.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController) {
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

                MyTextFieldComponent(labelValue = stringResource(id = R.string.email))
                PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password))

                Spacer(modifier = Modifier.height(30.dp))

                ButtonComponent(value = stringResource(id = R.string.log_in)) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

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
