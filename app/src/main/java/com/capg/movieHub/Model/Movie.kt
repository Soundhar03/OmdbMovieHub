package com.capg.movieHub.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by Soundhar on 22/06/22
 */
data class Movie(
    @SerializedName("Title")
    var title: String?,
    @SerializedName("Year")
    var year: String?,
    @SerializedName("imdbID")
    var imdbID: String?,
    @SerializedName("Type")
    var type: String?,
    @SerializedName("Poster")
    var poster: String?
)