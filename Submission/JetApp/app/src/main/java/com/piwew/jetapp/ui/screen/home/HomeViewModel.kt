package com.piwew.jetapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piwew.jetapp.data.HeroRepository
import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Map<Char, List<HeroItem>>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Map<Char, List<HeroItem>>>> get() = _uiState

    fun getAllHero() {
        viewModelScope.launch {
            repository.getSortedAndGroupedHeroes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { groupedHeroItems ->
                    _uiState.value = UiState.Success(groupedHeroItems)
                }
        }
    }
}