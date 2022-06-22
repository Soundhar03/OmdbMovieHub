package com.capg.movieHub.ombdApiService

import com.capg.movieHub.Model.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api details.
 * Created by Soundhar on 22/06/22
 */

const val OMBD_API_KEY = "b9bd48a6"

interface MovieApi {
    @GET("/")
    fun search(@Query("s") searchText: String, @Query("apikey") ombd_api_key: String = OMBD_API_KEY): Call<Search>

    @GET("/")
    fun getMovie(@Query("i") movieId: String, @Query("apikey") ombd_api_key: String = OMBD_API_KEY): Call<Search>
}