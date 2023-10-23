package com.iesam.androidviews.data.remote.api

import com.iesam.androidviews.domain.Category

fun CategoryApiModel.toModel(): Category =
    Category("1", this.urlImage, this.title, this.description)