package com.iesam.androidviews.data.local

import android.content.Context
import com.iesam.androidviews.app.Either
import com.iesam.androidviews.app.ErrorApp
import com.iesam.androidviews.app.left
import com.iesam.androidviews.app.right
import com.iesam.androidviews.app.serialization.JsonSerialization
import com.iesam.androidviews.domain.Category

class CategoryLocalDataSource(
    private val context: Context,
    private val serialization: JsonSerialization
) {

    private val sharedPref = context.getSharedPreferences("category", Context.MODE_PRIVATE)
    private val categoryId = "1"

    fun getCategory(): Either<ErrorApp, Category> {
        val jsonCategory = sharedPref.getString(categoryId, null)
        jsonCategory?.let {
            return serialization.fromJson(it, Category::class.java).right()
        }
        return ErrorApp.DataError.left()
    }

    fun save(category: Category) {
        sharedPref.edit().apply {
            putString(categoryId, serialization.toJson(category, Category::class.java))
            apply()
        }
    }

}