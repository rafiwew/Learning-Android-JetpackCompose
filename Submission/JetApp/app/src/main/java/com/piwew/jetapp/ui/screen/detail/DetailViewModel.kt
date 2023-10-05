package com.piwew.jetapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piwew.jetapp.data.HeroRepository
import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<HeroItem>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<HeroItem>> get() = _uiState

    fun getHeroById(heroId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getHeroItemById(heroId))
        }
    }

    fun addToFavorites(heroId: String) {
        viewModelScope.launch {
            repository.addToFavorites(heroId)
        }
    }

    fun removeFromFavorites(heroId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(heroId)
        }
    }

    fun checkFavorite(heroId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(heroId)
            onResult(isFavorite)
        }
    }
}