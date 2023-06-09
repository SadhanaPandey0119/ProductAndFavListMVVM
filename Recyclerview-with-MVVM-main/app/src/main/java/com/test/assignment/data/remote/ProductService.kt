package com.test.assignment.data.remote

import com.test.assignment.data.entities.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("v3/2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getAllProducts(): Response<ProductList>
}
