package com.piwew.jetapp.data

import com.piwew.jetapp.model.Hero
import com.piwew.jetapp.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }
}