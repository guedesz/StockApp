package com.example.stockapp.data.daos

import androidx.room.*
import com.example.stockapp.data.objects.Receita
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitaDao {

    @Query("select * from receitas")
    fun list(): Flow<List<Receita>>
    @Insert
    suspend fun set(receita: Receita)
    @Update
    suspend fun update(receita: Receita)
    @Query("DELETE FROM receitas WHERE id = :id")
    suspend fun delete(id: Int)
    @Query("delete from receitas")
    suspend fun deleteAll()
}
