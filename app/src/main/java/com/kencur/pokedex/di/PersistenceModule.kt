package com.kencur.pokedex.di

import android.app.Application
import androidx.room.Room
import com.kencur.pokedex.persistence.AppDatabase
import com.kencur.pokedex.persistence.PokemonDao
import com.kencur.pokedex.persistence.PokemonItemConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
        pokemonItemConverter: PokemonItemConverter
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "pokedex.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .addTypeConverter(pokemonItemConverter)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDatabase: AppDatabase): PokemonDao {
        return appDatabase.pokemonDao()
    }

    @Provides
    @Singleton
    fun providePokemonItemConverter(moshi: Moshi): PokemonItemConverter {
        return PokemonItemConverter(moshi)
    }
}
