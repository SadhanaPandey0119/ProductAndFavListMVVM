package com.test.assignment.data.remote

import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val productService: ProductService
) : BaseDataSource() {
    suspend fun getAllProduct() = getResult { productService.getAllProducts() }
}
