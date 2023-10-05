package com.piwew.jetapp.data

import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.model.HeroesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HeroRepository {

    private val heroItem = mutableListOf<HeroItem>()
    private val favoriteHeroes = mutableListOf<String>()

    init {
        if (heroItem.isEmpty()) {
            HeroesData.heroes.forEach {
                heroItem.add(HeroItem(it, 0))
            }
        }
    }

    fun getHeroItemById(heroId: String): HeroItem {
        return heroItem.first {
            it.item.id == heroId
        }
    }

    fun getSortedAndGroupedHeroes(): Flow<Map<Char, List<HeroItem>>> {
        return flow {
            val sortedHeroes = heroItem.sortedBy { it.item.name }
            val groupedHeroes = sortedHeroes.groupBy { it.item.name[0] }
            emit(groupedHeroes)
        }
    }

    fun searchHeroes(query: String): Flow<List<HeroItem>> {
        return flow {
            val filteredHeroes = heroItem.filter {
                it.item.name.contains(query, ignoreCase = true)
            }
            emit(filteredHeroes)
        }
    }

    fun getFavoriteHeroes(): Flow<List<HeroItem>> {
        return flow {
            val favoriteHeroItems = heroItem.filter { it.item.id in favoriteHeroes }
            emit(favoriteHeroItems)
        }
    }

    fun addToFavorites(heroId: String) {
        if (!favoriteHeroes.contains(heroId)) {
            favoriteHeroes.add(heroId)
        }
    }

    fun removeFromFavorites(heroId: String) {
        favoriteHeroes.remove(heroId)
    }

    fun isFavorite(heroId: String): Boolean {
        return favoriteHeroes.contains(heroId)
    }

    companion object {
        @Volatile
        private var instance: HeroRepository? = null

        fun getInstance(): HeroRepository = instance ?: synchronized(this) {
            HeroRepository().apply {
                instance = this
            }
        }
    }
}