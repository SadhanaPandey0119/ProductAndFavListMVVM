package com.test.assignment.ui.product

import androidx.lifecycle.ViewModel
import com.test.assignment.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val productList = repository.getAllProduct()
}
