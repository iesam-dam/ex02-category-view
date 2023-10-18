package com.iesam.androidviews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iesam.androidviews.app.ErrorApp
import com.iesam.androidviews.domain.Category
import com.iesam.androidviews.domain.GetCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoryViewModel(private val getCategoryUseCase: GetCategoryUseCase) : ViewModel() {

    private val _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel> = _uiModel

    fun loadCategory() {
        _uiModel.value = UiModel(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            getCategoryUseCase().fold(
                { responseError(it) },
                { responseSuccess(it) }
            )
        }
    }

    private fun responseError(error: ErrorApp) {
        _uiModel.postValue(UiModel(error = error))
    }

    private fun responseSuccess(category: Category) {
        _uiModel.postValue(UiModel(category = category))
    }

    data class UiModel(
        val isLoading: Boolean = false,
        val error: ErrorApp? = null,
        val category: Category? = null
    )
}