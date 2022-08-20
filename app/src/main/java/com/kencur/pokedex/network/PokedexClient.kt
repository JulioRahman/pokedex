package com.kencur.pokedex.network

import com.kencur.pokedex.model.Pagination
import com.kencur.pokedex.model.Pokemon
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class PokedexClient @Inject constructor(
    private val pokedexService: PokedexService
) {

    suspend fun fetchPokemonList(
        page: Int
    ): ApiResponse<Pagination> =
        pokedexService.fetchPokemonList(
            limit = PAGING_SIZE,
            offset = page * PAGING_SIZE
        )

    suspend fun fetchPokemon(
        name: String
    ): ApiResponse<Pokemon> =
        pokedexService.fetchPokemon(
            name = name
        )

    companion object {
        private const val PAGING_SIZE = 10
    }
}
