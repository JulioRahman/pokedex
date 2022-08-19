package com.kencur.pokedex.persistence

import androidx.room.*
import com.kencur.pokedex.model.PokemonInfo

@Dao
interface PokemonInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfo)

    @Query("SELECT * FROM PokemonInfo WHERE name = :name_")
    suspend fun getPokemonInfo(name_: String): PokemonInfo?

    @Query("SELECT * FROM PokemonInfo WHERE page = :page_")
    suspend fun getPokemonInfoList(page_: Int): List<PokemonInfo>

    @Query("SELECT * FROM PokemonInfo WHERE page <= :page_")
    suspend fun getAllPokemonInfoList(page_: Int): List<PokemonInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePokemon(pokemonInfo: PokemonInfo)

    @Update
    suspend fun removeFavoritePokemon(pokemonInfo: PokemonInfo)

    @Query("SELECT EXISTS(SELECT * FROM PokemonInfo WHERE id = :id_ AND isFavorite = 1)")
    fun isFavoritePokemon(id_: Int): Boolean

    @Query("SELECT * FROM PokemonInfo WHERE isFavorite = 1 ORDER BY id ASC")
    suspend fun getFavoritePokemonList(): List<PokemonInfo>
}
