package com.kencur.pokedex.persistence

import androidx.room.*
import com.kencur.pokedex.model.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon WHERE name = :name")
    suspend fun get(name: String): Pokemon?

    @Query("SELECT * FROM pokemon WHERE page = :page")
    suspend fun getAllByPage(page: Int): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE page <= :page")
    suspend fun getAllUntilPage(page: Int): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: Pokemon)

    @Update
    suspend fun removeFavorite(pokemon: Pokemon)

    @Query("SELECT EXISTS(SELECT * FROM pokemon WHERE id = :id AND is_favorite = 1)")
    fun isFavorite(id: Int): Boolean

    @Query("SELECT * FROM pokemon WHERE is_favorite = 1 ORDER BY id ASC")
    suspend fun getAllFavorite(): List<Pokemon>
}
