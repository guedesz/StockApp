package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.daos.ReceitaDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class ReceitaRepositorySQlite
@Inject constructor(private val receitaDao: ReceitaDao)
    : ReceitaRepository {

    override  val receitas: Flow<List<Receita>>
        get() = receitaDao.list()

    override suspend fun set(receita: Receita) {
        if (receita.id == 0){
            var id = receitaDao.set(receita)
            receita.id = id.toInt()
        } else {
            receitaDao.update(receita)
        }
    }

    suspend fun getDeletedReceitas(): List<Receita> {
        return receitaDao.getDeletedReceitas()
    }
    suspend fun getUnsyncedReceitas(): List<Receita> {
        return receitaDao.getUnsyncedReceitas()
    }

    // Método para atualizar o status de sincronização de várias receitas
    suspend fun updateSyncStatus(receitas: List<Receita>, isSynced: Boolean) {
        for (receita in receitas) {
            receita.isSynced = isSynced

            if (receita.isDeleted) {
                receitaDao.delete(receita.id)
            }
        }

        receitaDao.updateReceitas(receitas)
    }

    suspend fun updateLocalData(receitas: List<Receita>) {
        // Clear existing local data
        receitaDao.deleteAll()

        // Insert new data from Firebase
        // Insert new data from Firebase, avoiding duplicates
        receitas.forEach { receita ->
            // Check if a recipe with the same document ID already exists
            val existingRecipe = receitaDao.getReceitaByDocId(receita.docId)

            println(receita.docId)

            if (existingRecipe == null) {
                // Recipe with the same ID doesn't exist, insert it
                receitaDao.set(receita)
            } else {
                // Recipe with the same ID already exists, handle accordingly (update or skip)
                // Example: receitaDao.update(existingRecipe)
            }
        }
    }

    override suspend fun delete(receita: Receita){

        if (receita.isSynced || receita.docId.isNullOrEmpty()) {
            receitaDao.delete(receita.id)
        } else {
            receita.isDeleted = true
        }

        set(receita)
    }

    override suspend fun getReceitas(): List<Receita> {
        return receitaDao.list()
            .flatMapConcat { it.asFlow() }
            .toList()
    }

}

