package com.kencur.pokedex.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TypeResponse(

    @field:Json(name = "slot")
    val slot: Int,

    @field:Json(name = "type")
    val type: Type
) : Parcelable {

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Type(

        @field:Json(name = "name")
        val name: String
    ) : Parcelable
}
