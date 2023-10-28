package com.dicoding.picodiploma.productdetail

import android.content.Context

class RemoteDataSource(private val context: Context) {

    fun getDetailProduct(): ProductModel {
        return ProductModel(
            name = context.getString(R.string.name),
            price = context.getString(R.string.price),
            store = context.getString(R.string.store),
            date = context.getString(R.string.date),
            rating = context.getString(R.string.rating),
            countRating = context.getString(R.string.countRating),
            size = context.getString(R.string.size),
            color = context.getString(R.string.color),
            desc = context.getString(R.string.description),
            image = R.drawable.shoes,
            )
    }
}