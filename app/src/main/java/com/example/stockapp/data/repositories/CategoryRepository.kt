package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val categories: Flow<List<Category>>
    suspend fun set(category: Category)
    suspend fun delete(id: Int)
    suspend fun getCategoryByName(name: String): Category
}
