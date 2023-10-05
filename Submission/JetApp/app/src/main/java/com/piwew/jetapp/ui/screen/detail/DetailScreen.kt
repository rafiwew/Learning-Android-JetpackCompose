package com.piwew.jetapp.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.piwew.jetapp.R
import com.piwew.jetapp.di.Injection
import com.piwew.jetapp.helper.ViewModelFactory
import com.piwew.jetapp.ui.common.UiState
import com.piwew.jetapp.ui.components.FavoriteButton
import com.piwew.jetapp.ui.theme.JetAppTheme

@Composable
fun DetailScreen(
    heroId: String,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getHeroById(heroId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.item.photoUrl,
                    data.item.name,
                    onBackClick = navigateBack,
                    onAddToFavorite = navigateToCart
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    name: String,
    onBackClick: () -> Unit,
    onAddToFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onBackClick() }
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            tint = Color.White,
                            contentDescription = null,
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            FavoriteButton(text = "Tambahkan ke favorite", onClick = onAddToFavorite)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    JetAppTheme {
        DetailContent(name = "Muhammad Rafi", photoUrl = "", onBackClick = {}, onAddToFavorite = {})
    }
}