package com.piwew.jetapp.data

import com.piwew.jetapp.model.Hero
import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.model.HeroesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class HeroRepository {

    private val heroItem = mutableListOf<HeroItem>()
    private val favoriteHeroes = mutableListOf<String>()
    private val favoriteHeroesList = mutableListOf<Hero>()

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

    fun getFavoriteHeroes(): Flow<List<String>> {
        return flowOf(favoriteHeroes)
    }

    fun getFavoriteHeroesList(): Flow<List<Hero>> {
        favoriteHeroesList.clear()
        HeroesData.heroes.filter {
            favoriteHeroes.contains(it.id)
        }.forEach { hero ->
            favoriteHeroesList.add(hero)
        }
        return flowOf(favoriteHeroesList)
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