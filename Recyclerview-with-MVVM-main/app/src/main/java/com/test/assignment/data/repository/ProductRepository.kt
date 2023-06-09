package com.test.assignment.data.repository

import com.test.assignment.data.remote.ProductRemoteDataSource
import com.test.assignment.utils.performGetOperation
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) {
    fun getAllProduct() = performGetOperation(
        networkCall = { remoteDataSource.getAllProduct() }
    )
}
