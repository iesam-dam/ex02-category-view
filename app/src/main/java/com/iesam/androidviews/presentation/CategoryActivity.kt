package com.iesam.androidviews.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.faltenreich.skeletonlayout.Skeleton
import com.google.android.material.snackbar.Snackbar
import com.iesam.androidviews.R
import com.iesam.androidviews.app.ErrorApp
import com.iesam.androidviews.app.extension.loadUrl
import com.iesam.androidviews.app.serialization.GsonSerialization
import com.iesam.androidviews.data.CategoryDataRepository
import com.iesam.androidviews.data.local.CategoryLocalDataSource
import com.iesam.androidviews.data.remote.CategoryRemoteDataSource
import com.iesam.androidviews.databinding.ActivityCategoryBinding
import com.iesam.androidviews.domain.Category
import com.iesam.androidviews.domain.GetCategoryUseCase

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private val viewModel: CategoryViewModel by lazy {
        CategoryViewModel(
            GetCategoryUseCase(
                CategoryDataRepository(
                    CategoryLocalDataSource(
                        this,
                        GsonSerialization()
                    ), CategoryRemoteDataSource()
                )
            )
        )
    }

    private lateinit var skeleton: Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupView()
        setupObserver()
        viewModel.loadCategory()
    }

    private fun setupBinding() {
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupView() {
        skeleton = binding.layoutSkeleton
    }

    private fun setupObserver() {
        val observer = Observer<CategoryViewModel.UiModel> { uiModel ->
            if (uiModel.isLoading) {
                showLoading()
            } else {
                hideLoading()
            }

            uiModel.error?.let {
                showError(it)
            }

            uiModel.category?.let {
                bindData(it)
            }
        }
        viewModel.uiModel.observe(this, observer)
    }

    private fun showError(error: ErrorApp) {
        Snackbar.make(
            binding.root,
            getString(R.string.message_error),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showLoading() {
        skeleton.showSkeleton()
    }

    private fun hideLoading() {
        skeleton.showOriginal()
    }

    private fun bindData(category: Category) {
        binding.apply {
            image.loadUrl(category.urlImage)
            title.text = category.title
            description.text = category.description
        }
    }
}