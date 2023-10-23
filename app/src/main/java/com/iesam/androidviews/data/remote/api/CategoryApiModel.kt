package com.iesam.androidviews.data.remote.api

import com.google.gson.annotations.SerializedName
import com.iesam.androidviews.domain.Category

data class CategoryApiModel(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url_image") val urlImage: String
)
