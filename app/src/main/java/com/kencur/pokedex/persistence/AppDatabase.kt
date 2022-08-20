package com.kencur.pokedex.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kencur.pokedex.model.Pokemon

@Database(entities = [Pokemon::class], version = 6, exportSchema = true)
@TypeConverters(value = [PokemonItemConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}
