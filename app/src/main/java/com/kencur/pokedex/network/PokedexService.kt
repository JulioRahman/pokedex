package com.kencur.pokedex.network

import com.kencur.pokedex.model.Pagination
import com.kencur.pokedex.model.Pokemon
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): ApiResponse<Pagination>

    @GET("pokemon/{name}")
    suspend fun fetchPokemon(
        @Path("name") name: String
    ): ApiResponse<Pokemon>
}
