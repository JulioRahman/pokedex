package com.kencur.pokedex.di

import com.kencur.pokedex.network.PokedexClient
import com.kencur.pokedex.persistence.PokemonDao
import com.kencur.pokedex.repository.PokedexRepository
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
    fun providePokedexRepository(
        pokedexClient: PokedexClient,
        pokemonDao: PokemonDao,
        coroutineDispatcher: CoroutineDispatcher
    ): PokedexRepository {
        return PokedexRepository(pokedexClient, pokemonDao, coroutineDispatcher)
    }
}
