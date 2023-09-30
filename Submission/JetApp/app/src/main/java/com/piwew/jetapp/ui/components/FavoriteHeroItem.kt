package com.piwew.jetapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.piwew.jetapp.model.Hero
import com.piwew.jetapp.ui.screen.favorite.FavoriteViewModel

@Composable
fun FavoriteHeroItem(
    hero: Hero,
    viewModel: FavoriteViewModel,
    listOfFavorites: ArrayList<String>
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(220.dp),
        shape = RoundedCornerShape(5)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    AsyncImage(
                        model = hero.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = hero.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FavoritePreview() {
//    JetAppTheme {
//        FavoriteHeroItem(navController = , hero = , viewModel = , listOfFavorites = )
//    }
//}