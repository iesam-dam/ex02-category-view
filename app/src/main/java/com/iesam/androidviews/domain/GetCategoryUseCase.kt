package com.iesam.androidviews.domain

import com.iesam.androidviews.app.Either
import com.iesam.androidviews.app.ErrorApp

class GetCategoryUseCase(private val repository: CategoryRepository) {

    suspend operator fun invoke(): Either<ErrorApp, Category> {
        return repository.obtainCategory()
    }

}