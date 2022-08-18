package com.kencur.pokedex.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Stat(

    @field:Json(name = "base_stat")
    val value: Int
) : Parcelable
