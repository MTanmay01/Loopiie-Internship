package com.mtanmay.loopiieinternship.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(

    @SerializedName("id")
    val id: String,

    @SerializedName("url_z")
    val url: String,

//    @SerializedName("owner_name")
//    val ownerName: String
) : Parcelable
