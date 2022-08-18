package com.kencur.pokedex.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.kencur.pokedex.model.Sprites
import com.kencur.pokedex.model.Stat
import com.kencur.pokedex.model.TypeResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class PokemonItemConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun stringToTypeResponse(value: String): List<TypeResponse>? {
        val listType =
            Types.newParameterizedType(List::class.java, TypeResponse::class.java)
        val adapter: JsonAdapter<List<TypeResponse>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun typeResponseToString(type: List<TypeResponse>?): String {
        val listType =
            Types.newParameterizedType(List::class.java, TypeResponse::class.java)
        val adapter: JsonAdapter<List<TypeResponse>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun spritesToString(sprites: Sprites?): String {
        val adapter: JsonAdapter<Sprites> = moshi.adapter(Sprites::class.java)
        return adapter.toJson(sprites)
    }

    @TypeConverter
    fun stringToSprites(value: String): Sprites? {
        val adapter: JsonAdapter<Sprites> = moshi.adapter(Sprites::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun statToString(stat: List<Stat>?): String {
        val listType =
            Types.newParameterizedType(List::class.java, Stat::class.java)
        val adapter: JsonAdapter<List<Stat>> = moshi.adapter(listType)
        return adapter.toJson(stat)
    }

    @TypeConverter
    fun stringToStat(value: String): List<Stat>? {
        val listType =
            Types.newParameterizedType(List::class.java, Stat::class.java)
        val adapter: JsonAdapter<List<Stat>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }
}
