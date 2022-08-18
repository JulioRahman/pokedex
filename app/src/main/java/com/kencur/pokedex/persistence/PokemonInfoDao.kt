package com.kencur.pokedex.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kencur.pokedex.model.PokemonInfo

@Dao
interface PokemonInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfo)

    @Query("SELECT * FROM PokemonInfo WHERE name = :name_")
    suspend fun getPokemonInfo(name_: String): PokemonInfo?

    @Query("SELECT * FROM PokemonInfo WHERE page = :page_")
    suspend fun getPokemonInfoList(page_: Int): List<PokemonInfo>

    @Query("SELECT * FROM PokemonInfo WHERE page <= :page_")
    suspend fun getAllPokemonInfoList(page_: Int): List<PokemonInfo>
}
