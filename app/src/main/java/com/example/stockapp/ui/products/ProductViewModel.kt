package com.example.stockapp.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.Product
import com.example.stockapp.data.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject constructor(val repository: ProductRepository) : ViewModel() {

    var product: Product = Product()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        viewModelScope.launch {
            repository.products.collect { products ->
                _products.value = products
            }
        }
    }

    fun new() {
        product = Product()
    }

    fun edit(product: Product) {
        this.product = product
    }

    fun set() = viewModelScope.launch {
        repository.set(product)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }
}