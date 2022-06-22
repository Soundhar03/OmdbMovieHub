package com.capg.movieHub.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by Soundhar on 22/06/22
 */
data class Search(
    @SerializedName("Search")
    val resultSearch: List<Movie>?
)