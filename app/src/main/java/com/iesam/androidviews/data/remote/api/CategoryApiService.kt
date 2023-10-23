package com.iesam.androidviews.data.remote.api

import retrofit2.Response
import retrofit2.http.GET


interface CategoryApiService {
    //https://dam.sitehub.es/curso-2023-2024/api/youth-card-view.json
    @GET("youth-card-view.json")
    suspend fun getCategory(): Response<CategoryApiModel>
}