package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.daos.ReceitaDao
import kotlinx.coroutines.flow.Flow
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
        receitas.forEach { receita ->
            receitaDao.set(receita)
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

}

