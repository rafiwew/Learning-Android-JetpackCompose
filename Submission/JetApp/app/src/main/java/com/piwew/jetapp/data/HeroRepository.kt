package com.piwew.jetapp.data

import com.piwew.jetapp.model.HeroItem
import com.piwew.jetapp.model.HeroesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HeroRepository {

    private val heroItem = mutableListOf<HeroItem>()

    init {
        if (heroItem.isEmpty()) {
            HeroesData.heroes.forEach {
                heroItem.add(HeroItem(it, 0))
            }
        }
    }

    fun getAllHero(): Flow<List<HeroItem>> {
        return flowOf(heroItem)
    }

    fun getHeroItemById(heroId: String): HeroItem {
        return heroItem.first {
            it.item.id == heroId
        }
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