package com.assignment.onlinesales.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {
    @GET("/")
    fun getData(
        @Header("X-RapidAPI-Key") header: String?,
        /*@Header("X-RapidAPI-Host") headerHost: String?,*/
        @Query("expression") expression: String?
    ): Call<String?>?
}