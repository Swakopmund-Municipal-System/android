package com.example.swakopmundapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.components.AnnotatedClickableText
import com.example.swakopmundapp.ui.components.ButtonComponent
import com.example.swakopmundapp.ui.components.ClickablePart
import com.example.swakopmundapp.ui.components.HeadingTextComponent
import com.example.swakopmundapp.ui.components.MyTextFieldComponent
import com.example.swakopmundapp.ui.components.NormalTextComponent
import com.example.swakopmundapp.ui.components.PasswordTextFieldComponent
import com.example.swakopmundapp.ui.components.WhiteSheet
import java.time.format.TextStyle


@Composable
fun LoginScreen() {
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
            Spacer(modifier = Modifier.height(130.dp))
            Image(
                painter = painterResource(id = R.drawable.swakop_logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .padding(28.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(240.dp))

            WhiteSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .alpha(0.85f),
                cornerRadius = 24.dp
            ) {
                HeadingTextComponent(value = stringResource(id = R.string.login))
                NormalTextComponent(value = stringResource(id = R.string.welcome))
                MyTextFieldComponent(labelValue = stringResource(id = R.string.email))
                PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password))
                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(1.dp))
                ButtonComponent(value = stringResource(id = R.string.log_in))

                Spacer(modifier = Modifier.height(10.dp))
                AnnotatedClickableText(
                    parts = listOf(
                        ClickablePart("Don't have an account? "),
                        ClickablePart(text = "Sign Up", tag = "SIGNUP")
                    ),
                    defaultStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
                ) { tag ->
                    when (tag) {
                        "SIGNUP" -> {
                            //Here navigation to next screen.
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}