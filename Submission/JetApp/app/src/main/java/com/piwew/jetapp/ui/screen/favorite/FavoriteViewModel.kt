package com.piwew.jetapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piwew.jetapp.data.HeroRepository
import com.piwew.jetapp.model.Hero
import com.piwew.jetapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _favoriteHeroesListUiState: MutableStateFlow<UiState<List<Hero>>> =
        MutableStateFlow(UiState.Loading)
    val favoriteHeroesListUiState: StateFlow<UiState<List<Hero>>> get() = _favoriteHeroesListUiState

    private val _favoriteHeroesUiState: MutableStateFlow<UiState<List<String>>> =
        MutableStateFlow(UiState.Loading)
    val favoriteHeroesUiState: StateFlow<UiState<List<String>>> get() = _favoriteHeroesUiState

    fun getFavoriteHeroes() {
        viewModelScope.launch {
            repository.getFavoriteHeroes().catch {
                _favoriteHeroesUiState.value = UiState.Error(it.message.toString())
            }
                .collect { items ->
                    _favoriteHeroesUiState.value = UiState.Success(items)
                }
        }
    }

    fun getFavoriteHeroesList() {
        viewModelScope.launch {
            repository.getFavoriteHeroesList().catch {
                _favoriteHeroesListUiState.value = UiState.Error(it.message.toString())
            }
                .collect { items ->
                    _favoriteHeroesListUiState.value = UiState.Success(items)
                }
        }
    }

}