package com.iesam.androidviews.domain

import com.iesam.androidviews.app.Either
import com.iesam.androidviews.app.ErrorApp

interface CategoryRepository {

    suspend fun obtainCategory(): Either<ErrorApp, Category>

}