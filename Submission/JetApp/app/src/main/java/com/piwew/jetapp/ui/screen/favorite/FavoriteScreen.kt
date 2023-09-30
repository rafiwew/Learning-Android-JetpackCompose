package com.piwew.jetapp.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.piwew.jetapp.di.Injection
import com.piwew.jetapp.helper.ViewModelFactory
import com.piwew.jetapp.ui.common.UiState
import com.piwew.jetapp.ui.components.FavoriteHeroItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
) {
    viewModel.favoriteHeroesUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteHeroes()
            }

            is UiState.Success -> {
                val listOfFavoriteHeroes = uiState.data

                viewModel.favoriteHeroesListUiState.collectAsState(initial = UiState.Loading).value.let { uiState2 ->
                    when (uiState2) {
                        is UiState.Loading -> {
                            viewModel.getFavoriteHeroesList()
                        }

                        is UiState.Success -> {
                            if (uiState.data.isNotEmpty()) {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(170.dp),
                                    contentPadding = PaddingValues(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                                    verticalArrangement = Arrangement.spacedBy(3.dp),
                                    modifier = Modifier
                                        .testTag("SpecialSelectionList")
                                ) {
                                    items(uiState2.data) { data ->
                                        FavoriteHeroItem(
                                            hero = data,
                                            viewModel = viewModel,
                                            listOfFavorites = listOfFavoriteHeroes as ArrayList<String>
                                        )
                                    }
                                }
                            }
                        }

                        is UiState.Error -> {}
                    }
                }
            }

            is UiState.Error -> {}
        }
    }
}