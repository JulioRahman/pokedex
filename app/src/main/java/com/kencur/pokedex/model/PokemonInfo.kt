package com.kencur.pokedex.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class PokemonInfo(

    var page: Int = 0,

    @field:Json(name = "id") @PrimaryKey
    val id: Int,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "height")
    val height: Int,

    @field:Json(name = "weight")
    val weight: Int,

    @field:Json(name = "base_experience")
    val experience: Int,

    @field:Json(name = "types")
    val types: List<TypeResponse>,

    @field:Json(name = "stats")
    val stats: List<Stat>,

    @field:Json(name = "sprites")
    val sprites: Sprites,

    var isFavorite: Boolean = false
) : Parcelable {

    fun getIdString(): String = String.format("#%03d", id)

    fun getHeightString(): String = String.format("%d cm", height.times(10))

    fun getWeightString(): String = String.format("%.1f kg", weight.div(10.0f))

    fun getHp(): Int = stats[0].value

    fun getAttack(): Int = stats[1].value

    fun getDefense(): Int = stats[2].value

    fun getSpAttack(): Int = stats[3].value

    fun getSpDefense(): Int = stats[4].value

    fun getSpeed(): Int = stats[5].value

    fun getHpString(): String = getHp().toString()

    fun getAttackString(): String = getAttack().toString()

    fun getDefenseString(): String = getDefense().toString()

    fun getSpAttackString(): String = getSpAttack().toString()

    fun getSpDefenseString(): String = getSpDefense().toString()

    fun getSpeedString(): String = getSpeed().toString()

    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxDefense = 300
        const val maxSpAttack = 300
        const val maxSpDefense = 300
        const val maxSpeed = 300
    }
}
