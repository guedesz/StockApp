package com.example.stockapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("select * from products")
    fun list(): Flow<List<Product>>
    @Insert
    suspend fun set(product: Product)
    @Update
    suspend fun update(product: Product)
    @Query("DELETE FROM products WHERE id = :id")
    suspend fun delete(id: Int)
    @Query("delete from products")
    suspend fun deleteAll()
}
