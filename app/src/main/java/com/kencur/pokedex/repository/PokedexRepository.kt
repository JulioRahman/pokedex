package com.kencur.pokedex.repository

import androidx.annotation.WorkerThread
import com.kencur.pokedex.network.PokedexClient
import com.kencur.pokedex.persistence.PokemonInfoDao
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
    private val pokemonInfoDao: PokemonInfoDao,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun fetchPokemonInfoList(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val pokemonInfoList = pokemonInfoDao.getPokemonInfoList(page)
        if (pokemonInfoList.isEmpty()) {
            val pokemonResponse = pokedexClient.fetchPokemonList(page = page)
            pokemonResponse.suspendOnSuccess {
                data.results.forEach { pokemon ->
                    val pokemonInfo = pokemonInfoDao.getPokemonInfo(pokemon.name)
                    if (pokemonInfo == null) {
                        val pokemonInfoResponse =
                            pokedexClient.fetchPokemonInfo(name = pokemon.name)
                        pokemonInfoResponse.suspendOnSuccess {
                            pokemonInfoDao.insertPokemonInfo(data.copy(page = page))
                        }.onFailure {
                            onError(message())
                        }
                    }
                }
                emit(pokemonInfoDao.getAllPokemonInfoList(page).sortedBy { it.id })
            }.onFailure {
                onError(message())
            }
        } else {
            emit(pokemonInfoDao.getAllPokemonInfoList(page).sortedBy { it.id })
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}
