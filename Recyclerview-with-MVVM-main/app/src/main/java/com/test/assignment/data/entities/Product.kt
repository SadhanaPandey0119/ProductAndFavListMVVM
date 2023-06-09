package com.test.assignment.data.entities

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String,
    @SerializedName("imageURL") val imageURL: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("ratingCount") val ratingCount: Double,
    @SerializedName("price") val price: List<Price>,
    @SerializedName("addToCartButtonText") val addToCartButtonText: String,

)

data class Price(
    @SerializedName("isOfferPrice")
    val isOfferPrice: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("value")
    val value: Double
)
