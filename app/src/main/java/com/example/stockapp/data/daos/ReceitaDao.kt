package com.example.stockapp.data.daos

import androidx.room.*
import com.example.stockapp.data.objects.Receita
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitaDao {

    @Query("select * from receitas")
    fun list(): Flow<List<Receita>>
    @Insert//(onConflict =OnConflictStrategy.REPLACE)
    suspend fun set(receita: Receita): Long
    @Update
    suspend fun update(receita: Receita)
    @Query("DELETE FROM receitas WHERE id = :id")
    suspend fun delete(id: Int)
    @Query("delete from receitas")
    suspend fun deleteAll()

    @Query("SELECT * FROM receitas WHERE docId = :docId")
    suspend fun getReceitaByDocId(docId: String): Receita?
    @Query("SELECT * FROM receitas WHERE id = :id")
    suspend fun getReceitaById(id: Int): Receita?

    @Query("SELECT * FROM receitas WHERE isSynced = 0")
    suspend fun getUnsyncedReceitas(): List<Receita>
    @Update
    suspend fun updateReceitas(receitas: List<Receita>)

    @Query("SELECT * FROM receitas WHERE isDeleted = 1")
    suspend fun getDeletedReceitas(): List<Receita>

}
