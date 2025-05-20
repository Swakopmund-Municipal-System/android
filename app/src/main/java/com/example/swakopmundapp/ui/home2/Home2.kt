package com.example.swakopmundapp.ui.home2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.components.BottomNavBar
import com.example.swakopmundapp.ui.components.MenuTile


private val DefaultMenuItems = listOf(
    R.drawable.municipal             to "Municipal",
    R.drawable.about                 to "About",
    R.drawable.tourism               to "Tourism",
    R.drawable.community             to "Community",
    R.drawable.support               to "Support",
    R.drawable.currency_converter    to "Currency Converter",
    R.drawable.weather               to "Weather",
    R.drawable.favourite_memories    to "Favourite Memories",
    R.drawable.where_to_stay         to "Where To Stay"
)

private val DefaultBottomNavItems = listOf(
    R.drawable.home          to "Home",
    R.drawable.explore       to "Explore",
    R.drawable.announcements to "Announcements",
    R.drawable.profile       to "Profile"
)

@Composable
fun HomeScreen(
    headerImage: Int = R.drawable.background2,
    menuItems: List<Pair<Int, String>> = DefaultMenuItems,
    bottomNavItems: List<Pair<Int, String>> = DefaultBottomNavItems
) {

    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = headerImage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { i ->
                Box(
                    Modifier
                        .size(if (i == 0) 10.dp else 8.dp)
                        .background(
                            color = if (i == 0) Color.White else Color.LightGray,
                            shape = CircleShape
                        )
                )
                if (i < 2) Spacer(Modifier.width(6.dp))
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            menuItems.chunked(3).forEach { row ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { (icon, label) ->
                        MenuTile(iconRes = icon, label = label)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBar(
            items = bottomNavItems,
            selectedIndex = selectedTab,
            onItemSelected = { selectedTab = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}