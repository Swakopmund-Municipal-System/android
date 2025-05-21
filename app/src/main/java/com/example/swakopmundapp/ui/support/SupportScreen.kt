package com.example.swakopmundapp.ui.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swakopmundapp.R

@Composable
fun SupportScreen(onBack: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(colorResource(id = R.color.bluebar)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Support",
                color = Color.White,
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}

//package com.example.swakopmundapp.ui.support
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.swakopmundapp.R
//
//@Composable
//fun SupportScreen(onBack: () -> Unit = {}) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp) // Standard app bar height
//                .background(color = colorResource(id = R.color.bluebar))
//        ) {
//            IconButton(
//                onClick = onBack,
//                modifier = Modifier
//                    .align(Alignment.CenterStart)
//                    .padding(start = 8.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color.White
//                )
//            }
//
//            Text(
//                text = "Support",
//                color = Color.White,
//                fontSize = 20.sp,
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//
//        // Placeholder for the rest of the screen
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("Support Content Goes Here")
//        }
//    }
//}
