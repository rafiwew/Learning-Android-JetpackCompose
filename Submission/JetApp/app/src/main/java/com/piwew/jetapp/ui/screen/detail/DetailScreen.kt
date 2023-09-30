package com.piwew.jetapp.ui.screen.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.piwew.jetapp.di.Injection
import com.piwew.jetapp.helper.ViewModelFactory
import com.piwew.jetapp.ui.common.UiState
import com.piwew.jetapp.ui.theme.JetAppTheme

@Composable
fun DetailScreen(
    heroId: String,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getHeroById(heroId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.item.name
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    name: String,
) {
    Text(text = name, style = MaterialTheme.typography.bodyLarge)
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    JetAppTheme {
        DetailContent(name = "Muhammad Rafi")
    }
}