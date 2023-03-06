package com.amlavati.artbook.api

import com.amlavati.artbook.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {


    @GET("api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<Result>
}