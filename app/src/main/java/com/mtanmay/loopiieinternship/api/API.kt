package com.mtanmay.loopiieinternship.api

import androidx.annotation.NonNull
import com.mtanmay.loopiieinternship.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    companion object {
        const val BASE_URL = "https://api.flickr.com/services/rest/"
    }

    @GET("?method=flickr.photos.getRecent")
    suspend fun getRecentPhotos(

        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,

        @Query("extras")
        extras: String = "url_z",

        @Query("per_page")
        perPage: Int = 100,

        @Query("format")
        format: String = "json",

        @Query("nojsoncallback")
        noJsonCallback: String = "1"

    ): Response

    @GET("?method=flickr.photos.search")
    suspend fun getSearchPhotos(

        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,

        @NonNull
        @Query("text")
        query: String,

        @Query("extras")
        extras: String = "url_z",

        @Query("per_page")
        perPage: Int = 20,

        @Query("format")
        format: String = "json",

        @Query("nojsoncallback")
        noJsonCallback: String = "1"

    ): Response

}