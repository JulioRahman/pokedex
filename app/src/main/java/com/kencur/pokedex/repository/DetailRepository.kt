package com.kencur.pokedex.repository

import androidx.annotation.WorkerThread
import com.kencur.pokedex.mapper.ErrorResponseMapper
import com.kencur.pokedex.model.PokemonErrorResponse
import com.kencur.pokedex.model.PokemonInfo
import com.kencur.pokedex.network.PokedexClient
import com.kencur.pokedex.persistence.PokemonInfoDao
import com.skydoves.sandwich.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val pokedexClient: PokedexClient,
    private val pokemonInfoDao: PokemonInfoDao,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun fetchPokemonInfo(
        name: String,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val pokemonInfo = pokemonInfoDao.getPokemonInfo(name)
        if (pokemonInfo == null) {
            /**
             * fetches a [PokemonInfo] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#apiresponse-extensions-for-coroutines)
             */
            val response = pokedexClient.fetchPokemonInfo(name = name)
            response.suspendOnSuccess {
                pokemonInfoDao.insertPokemonInfo(data)
                emit(data)
            }
                // handles the case when the API request gets an error response.
                // e.g., internal server error.
                .onError {
                    /** maps the [ApiResponse.Failure.Error] to the [PokemonErrorResponse] using the mapper. */
                    map(ErrorResponseMapper) { onError("[Code: $code]: $message") }
                }
                // handles the case when the API request gets an exception response.
                // e.g., network connection error.
                .onException { onError(message) }
        } else {
            emit(pokemonInfo)
        }
    }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}
