package com.kencur.pokedex.repository

import androidx.annotation.WorkerThread
import com.kencur.pokedex.model.Pokemon
import com.kencur.pokedex.network.PokedexClient
import com.kencur.pokedex.persistence.PokemonDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val pokedexClient: PokedexClient,
    private val pokemonDao: PokemonDao,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun fetchPokemonList(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val pokemonList = pokemonDao.getPokemonList(page)
        if (pokemonList.isEmpty()) {
            val pokemonListResponse = pokedexClient.fetchPokemonList(page = page)
            pokemonListResponse.suspendOnSuccess {
                data.results.forEach { pokemonResult ->
                    val pokemon = pokemonDao.getPokemon(pokemonResult.name)
                    if (pokemon == null) {
                        val pokemonResponse = pokedexClient.fetchPokemon(name = pokemonResult.name)
                        pokemonResponse.suspendOnSuccess {
                            pokemonDao.insertPokemon(data.copy(page = page))
                        }.onFailure {
                            onError(message())
                        }
                    }
                }
                emit(pokemonDao.getAllPokemonList(page).sortedBy { it.id })
            }.onFailure {
                onError(message())
            }
        } else {
            emit(pokemonDao.getAllPokemonList(page).sortedBy { it.id })
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    @WorkerThread
    fun isFavoritePokemon(id: Int) = pokemonDao.isFavoritePokemon(id)

    @WorkerThread
    suspend fun addFavoritePokemon(pokemon: Pokemon) =
        pokemonDao.addFavoritePokemon(pokemon)

    @WorkerThread
    suspend fun removeFavoritePokemon(pokemon: Pokemon) =
        pokemonDao.removeFavoritePokemon(pokemon)

    @WorkerThread
    fun fetchFavoritePokemonList(
        onStart: () -> Unit,
        onComplete: () -> Unit
    ) = flow {
        emit(pokemonDao.getFavoritePokemonList())
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}
