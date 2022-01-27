package com.mtanmay.loopiieinternship.api

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("photos")
    val photos: Result
) {
    data class Result(

        @SerializedName("photo")
        val photo: List<Photo>
    )
}