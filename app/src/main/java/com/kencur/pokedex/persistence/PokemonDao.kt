package com.kencur.pokedex.persistence

import androidx.room.*
import com.kencur.pokedex.model.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon WHERE name = :name_")
    suspend fun getPokemon(name_: String): Pokemon?

    @Query("SELECT * FROM pokemon WHERE page = :page_")
    suspend fun getPokemonList(page_: Int): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE page <= :page_")
    suspend fun getAllPokemonList(page_: Int): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePokemon(pokemon: Pokemon)

    @Update
    suspend fun removeFavoritePokemon(pokemon: Pokemon)

    @Query("SELECT EXISTS(SELECT * FROM pokemon WHERE id = :id_ AND is_favorite = 1)")
    fun isFavoritePokemon(id_: Int): Boolean

    @Query("SELECT * FROM pokemon WHERE is_favorite = 1 ORDER BY id ASC")
    suspend fun getFavoritePokemonList(): List<Pokemon>
}
