package com.example.stockapp.data

import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    val products: Flow<List<Product>>
    suspend fun set(product: Product)
    suspend fun delete(id: Int)
}
