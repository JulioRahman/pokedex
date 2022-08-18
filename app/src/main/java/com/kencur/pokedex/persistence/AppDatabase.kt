package com.kencur.pokedex.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kencur.pokedex.model.Pokemon
import com.kencur.pokedex.model.PokemonInfo

@Database(entities = [Pokemon::class, PokemonInfo::class], version = 3, exportSchema = true)
@TypeConverters(value = [PokemonItemConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonInfoDao(): PokemonInfoDao
}
