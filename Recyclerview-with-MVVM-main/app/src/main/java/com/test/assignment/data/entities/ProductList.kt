package com.test.assignment.data.entities

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("products") val products: List<Product>
)
