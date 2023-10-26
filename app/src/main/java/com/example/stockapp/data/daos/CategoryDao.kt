package com.example.stockapp.data.daos

import androidx.room.*
import com.example.stockapp.data.objects.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getCategoryByName(name: String): Category

    @Query("select * from categories")
    fun list(): Flow<List<Category>>
    @Insert
    suspend fun set(category: Category)
    @Update
    suspend fun update(category: Category)
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun delete(id: Int)
    @Query("delete from categories")
    suspend fun deleteAll()
}
