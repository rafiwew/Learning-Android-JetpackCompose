package com.piwew.jetheroes.data

import com.piwew.jetheroes.model.Hero
import com.piwew.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }
}