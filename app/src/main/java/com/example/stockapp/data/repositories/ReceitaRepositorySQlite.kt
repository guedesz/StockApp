package com.example.stockapp.data.repositories

import android.util.Log
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.daos.ReceitaDao
import com.example.stockapp.data.objects.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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

    suspend fun updateLocalData(receitas: List<Receita>) {
        // Clear existing local data
        receitaDao.deleteAll()

        // Insert new data from Firebase
        receitas.forEach { receita ->
            receitaDao.set(receita)
        }

    }
    override suspend fun delete(receita: Receita){
        receitaDao.delete(receita.id)
    }

}

