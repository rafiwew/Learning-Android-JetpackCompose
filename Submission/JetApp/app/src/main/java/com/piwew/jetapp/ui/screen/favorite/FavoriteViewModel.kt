package com.piwew.jetapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piwew.jetapp.data.HeroRepository
import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<HeroItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<HeroItem>>> get() = _uiState

    val favoriteHeroes: Flow<List<HeroItem>> = repository.getFavoriteHeroes()

    fun getAllFavoriteHeroes() {
        viewModelScope.launch {
            repository.getFavoriteHeroes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteHeroItems ->
                    _uiState.value = UiState.Success(favoriteHeroItems)
                }
        }
    }

}