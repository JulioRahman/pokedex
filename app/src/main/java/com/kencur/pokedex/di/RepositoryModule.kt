package com.kencur.pokedex.di

import com.kencur.pokedex.network.PokedexClient
import com.kencur.pokedex.persistence.PokemonDao
import com.kencur.pokedex.persistence.PokemonInfoDao
import com.kencur.pokedex.repository.DetailRepository
import com.kencur.pokedex.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        pokedexClient: PokedexClient,
        pokemonDao: PokemonDao,
        coroutineDispatcher: CoroutineDispatcher
    ): MainRepository {
        return MainRepository(pokedexClient, pokemonDao, coroutineDispatcher)
    }

    @Provides
    @ViewModelScoped
    fun provideDetailRepository(
        pokedexClient: PokedexClient,
        pokemonInfoDao: PokemonInfoDao,
        coroutineDispatcher: CoroutineDispatcher
    ): DetailRepository {
        return DetailRepository(pokedexClient, pokemonInfoDao, coroutineDispatcher)
    }
}
