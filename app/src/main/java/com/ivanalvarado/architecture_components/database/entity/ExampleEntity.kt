package com.ivanalvarado.architecture_components.database.entity

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class ExampleEntity(
    @SerializedName("id")
    val id: Long,

    @SerializedName(value = "header", alternate = ["title", "name"])
    val header: String,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName(value = "description", alternate = ["overview", "synopsis"])
    var description: String?,

    @SerializedName("release_date")
    var releaseDate: String?,

    @SerializedName("runtime")
    var runTime: Long,
    var status: String?
)