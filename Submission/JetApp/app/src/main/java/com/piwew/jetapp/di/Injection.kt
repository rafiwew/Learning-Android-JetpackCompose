package com.piwew.jetapp.di

import com.piwew.jetapp.data.HeroRepository

object Injection {
    fun provideRepository(): HeroRepository {
        return HeroRepository.getInstance()
    }
}