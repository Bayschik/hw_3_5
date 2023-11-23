package com.example.lesson_3_5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://pixabay.com/api/

interface PixaApi {

    @GET("api/")
    fun getImages(
        @Query("key") key: String = "40842539-047d68f9f0dbb6c40be9b0508",
        @Query("q") keyWord: String,
        @Query("per_page") perPage: Int = 3,
        @Query("page") page: Int = 1,
    ):Call<PixaModel>
}