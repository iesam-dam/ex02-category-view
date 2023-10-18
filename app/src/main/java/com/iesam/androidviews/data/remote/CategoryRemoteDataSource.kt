package com.iesam.androidviews.data.remote

import com.iesam.androidviews.app.Either
import com.iesam.androidviews.app.ErrorApp
import com.iesam.androidviews.app.right
import com.iesam.androidviews.domain.Category

class CategoryRemoteDataSource {

    fun getCategory(): Either<ErrorApp, Category> =
        Category(
            "1",
            "https://dam.sitehub.es/curso-2023-2024/api/images/hotels-01.png",
            "Alojamientos",
            "Disfruta de ofertas en hoteles, apartamentos, hostales, etc."
        ).right()

}