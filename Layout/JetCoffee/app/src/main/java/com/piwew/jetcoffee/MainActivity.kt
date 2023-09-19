package com.piwew.jetcoffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piwew.jetcoffee.model.BottomBarItem
import com.piwew.jetcoffee.model.Menu
import com.piwew.jetcoffee.model.dummyBestSellerMenu
import com.piwew.jetcoffee.model.dummyCategory
import com.piwew.jetcoffee.model.dummyMenu
import com.piwew.jetcoffee.ui.components.CategoryItem
import com.piwew.jetcoffee.ui.components.HomeSection
import com.piwew.jetcoffee.ui.components.MenuItem
import com.piwew.jetcoffee.ui.components.Search
import com.piwew.jetcoffee.ui.theme.JetCoffeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCoffeeTheme {
                JetCoffeeApp()
            }
        }
    }
}

@Composable
fun JetCoffeeApp() {
    Scaffold(
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Banner()
            HomeSection(
                title = stringResource(R.string.section_category),
                content = { CategoryRow() }
            )
            HomeSection(
                title = stringResource(R.string.menu_favorite),
                content = { MenuRow(dummyMenu) }
            )
            HomeSection(
                title = stringResource(R.string.section_best_seller_menu),
                content = { MenuRow(dummyBestSellerMenu) }
            )
        }
    }
}

@Composable
fun Banner(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "Banner image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp)
        )
        Search()
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(dummyCategory, key = { it.textCategory }) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun MenuRow(
    listMenu: List<Menu>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(listMenu, key = { it.title }) { menu ->
            MenuItem(menu)
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    NavigationBar(containerColor = Color.White, modifier = modifier) {
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle
            )
        )
        navigationItems.map {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(it.title)
                },
                selected = it.title == navigationItems[0].title,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryRowPreview() {
    JetCoffeeTheme {
        CategoryRow()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun JetCoffeeAppPreview() {
    JetCoffeeTheme {
        JetCoffeeApp()
    }
}

@Preview(showBackground = true)
@Composable
fun FormInputPreview() {
    JetCoffeeTheme {
        FormInput()
    }
}

@Composable
fun FormInput() {
    var name by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = name,
        onValueChange = { newName ->
            name = newName
        },
        label = { Text("Nama") },
        modifier = Modifier.padding(8.dp)
    )
}