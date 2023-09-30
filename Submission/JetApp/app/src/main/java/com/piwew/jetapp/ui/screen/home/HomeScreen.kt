package com.piwew.jetapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.piwew.jetapp.di.Injection
import com.piwew.jetapp.helper.ViewModelFactory
import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.ui.common.UiState
import com.piwew.jetapp.ui.components.HeroListItem
import com.piwew.jetapp.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit
) {
    val searchResult by viewModel.searchResult.collectAsState(initial = emptyList())
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllHero()
            }

            is UiState.Success -> {
                Column {
                    SearchBar(
                        query = query,
                        onQueryChange = { newQuery ->
                            viewModel.setQuery(newQuery)
                            viewModel.searchHeroes()
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    )
                    HomeContent(
                        groupedHeroes = if (query.isEmpty()) uiState.data else emptyMap(),
                        searchResult = searchResult,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    groupedHeroes: Map<Char, List<HeroItem>>,
    searchResult: List<HeroItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            if (searchResult.isNotEmpty()) {
                items(searchResult) { data ->
                    HeroListItem(
                        name = data.item.name,
                        photoUrl = data.item.photoUrl,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.item.id)
                        }
                    )
                }
            } else {
                groupedHeroes.entries.forEach { (_, heroItems) ->
                    items(heroItems) { data ->
                        HeroListItem(
                            name = data.item.name,
                            photoUrl = data.item.photoUrl,
                            modifier = Modifier.clickable {
                                navigateToDetail(data.item.id)
                            }
                        )
                    }
                }
            }
        }
    }
}