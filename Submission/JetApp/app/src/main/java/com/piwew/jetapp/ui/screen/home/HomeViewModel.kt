package com.piwew.jetapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.piwew.jetapp.data.HeroRepository
import com.piwew.jetapp.model.Hero
import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.ui.common.UiState
import com.piwew.jetapp.ui.screen.detail.DetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<HeroItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<HeroItem>>> get() = _uiState

    fun getAllHero() {
        viewModelScope.launch {
            repository.getAllHero()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { heroItem ->
                    _uiState.value = UiState.Success(heroItem)
                }
        }
    }
}