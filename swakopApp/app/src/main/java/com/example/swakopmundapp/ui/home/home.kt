package com.example.swakopmundapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CurrencyExchange
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Support
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar
import com.example.swakopmundapp.ui.shared.MenuGridItem

data class MenuItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

private val SlideshowImages = listOf(
    R.drawable.background2,
    R.drawable.slideshow_image_2,
    R.drawable.slideshow_image_3,
    R.drawable.slideshow_image_4
)


data class DiscoverNowItem(
    val imageResId: Int,
    val title: String,
    val route: String
)


private val DiscoverNowItems = listOf(
    DiscoverNowItem(R.drawable.slideshow_image_2, "Beaches", Screen.TourismGrid.route),
    DiscoverNowItem(R.drawable.sky, "Activities", Screen.TourismGrid.route),
    DiscoverNowItem(R.drawable.restaurant, "Restaurants", Screen.TourismGrid.route),
    DiscoverNowItem(R.drawable.background2, "History", Screen.TourismGrid.route)
)


@Composable
fun DiscoverNowCard(
    item: DiscoverNowItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(150.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp) 
                .align(Alignment.BottomStart)
        ) {
            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun HomeScreen(navController: NavHostController) {
    val menuItems = listOf(
        MenuItem(
            icon = Icons.Outlined.AccountBalance,
            label = "Municipal",
            route = Screen.Municipal.route
        ),
        MenuItem(
            icon = Icons.Outlined.Info,
            label = "About",
            route = Screen.About.route
        ),
        MenuItem(
            icon = Icons.Outlined.LocationCity,
            label = "Tourism",
            route = Screen.TourismGrid.route
        ),
        MenuItem(
            icon = Icons.Outlined.People,
            label = "Community",
            route = Screen.Community.route
        ),
        MenuItem(
            icon = Icons.Outlined.Support,
            label = "Support",
            route = Screen.Support.route
        ),
        MenuItem(
            icon = Icons.Outlined.CurrencyExchange,
            label = "Currency\nConverter",
            route = Screen.CurrencyConverter.route
        ),
        MenuItem(
            icon = Icons.Outlined.WbSunny,
            label = "Weather",
            route = Screen.Weather.route
        ),
        MenuItem(
            icon = Icons.Outlined.GridView,
            label = "Favourite\nMemories",
            route = Screen.FavouriteMemories.route
        ),
        MenuItem(
            icon = Icons.Outlined.Place,
            label = "Where To\nStay",
            route = Screen.WhereToStay.route
        )
    )

    Scaffold(
        topBar = { TopBlueBar(title = "Home") },
        bottomBar = {

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            BottomNavBar(
                currentRoute = Screen.Home.route,
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            ImageCarousel(
                images = SlideshowImages,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )


            Text(
                text = "Discover Now",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 8.dp)
            )


            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), 
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(DiscoverNowItems) { item ->
                    DiscoverNowCard(item = item, onClick = { navController.navigate(item.route) })
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) 


            Text(
                text = "Swakopmund Services",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )

           
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp), 
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(menuItems) { item ->
                    MenuGridItem(
                        icon = item.icon,
                        label = item.label,
                        onClick = {
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBlueBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, color = Color.White) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF0000FF) 
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    
}
