package com.example.swakopmundapp.ui.startscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.components.ButtonComponent
import com.example.swakopmundapp.ui.navigation.Screen


@Composable
fun StartScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.swakop_sc_bg),
            contentDescription = stringResource(id = R.string.background1),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(350.dp))
            Image(
                painter = painterResource(id = R.drawable.swakop_logo),
                contentDescription = stringResource(R.string.logo)
            )
            Spacer(modifier = Modifier.weight(1f))

            ButtonComponent(
                value = stringResource(R.string.get_started),
                onClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}