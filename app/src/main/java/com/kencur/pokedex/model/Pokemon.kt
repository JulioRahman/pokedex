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
data class Pokemon(

    @field:Json(name = "name") @PrimaryKey
    val name: String,

    @field:Json(name = "url")
    val url: String
) : Parcelable
