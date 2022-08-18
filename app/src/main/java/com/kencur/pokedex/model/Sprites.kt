package com.kencur.pokedex.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Sprites(

    @field:Json(name = "other")
    val other: Other
) : Parcelable {

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Other(

        @field:Json(name = "home")
        val home: Home
    ) : Parcelable

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Home(

        @field:Json(name = "front_default")
        val frontDefault: String
    ) : Parcelable
}
