package com.iesam.androidviews.data

import com.iesam.androidviews.app.Either
import com.iesam.androidviews.app.ErrorApp
import com.iesam.androidviews.data.local.CategoryLocalDataSource
import com.iesam.androidviews.data.remote.CategoryRemoteDataSource
import com.iesam.androidviews.domain.Category
import com.iesam.androidviews.domain.CategoryRepository

class CategoryDataRepository(
    private val local: CategoryLocalDataSource,
    private val remote: CategoryRemoteDataSource
) : CategoryRepository {


    override suspend fun obtainCategory(): Either<ErrorApp, Category> {
        var category = local.getCategory()
        category.mapLeft { errorApp ->
            return remote.getCategory().map { category ->
                local.save(category)
                category
            }
        }
        return category
    }
}