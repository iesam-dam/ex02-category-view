package com.iesam.androidviews.data.remote

import com.iesam.androidviews.app.Either
import com.iesam.androidviews.app.ErrorApp
import com.iesam.androidviews.app.left
import com.iesam.androidviews.app.right
import com.iesam.androidviews.data.remote.api.CategoryApiModel
import com.iesam.androidviews.data.remote.api.CategoryApiService
import com.iesam.androidviews.data.remote.api.toModel
import com.iesam.androidviews.domain.Category
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


class CategoryRemoteDataSource {

    private val baseUrl = "https://dam.sitehub.es/curso-2023-2024/api/"

    suspend fun getCategory(): Either<ErrorApp, Category> {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service: CategoryApiService = retrofit.create(CategoryApiService::class.java)

        try {
            val repos: Response<CategoryApiModel> = service.getCategory()

            if (repos.isSuccessful) {
                return repos.body()!!.toModel().right()
            } else {
                return ErrorApp.NetworkError.left()
            }
        } catch (ex: TimeoutException) {
            return ErrorApp.NetworkError.left()
        } catch (ex: UnknownHostException) {
            return ErrorApp.NetworkError.left()
        } catch (ex: Exception) {
            return ErrorApp.NetworkError.left()
        }
    }

}