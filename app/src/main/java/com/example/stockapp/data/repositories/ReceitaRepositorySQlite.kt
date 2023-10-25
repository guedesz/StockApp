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
            receitaDao.set(receita)
        } else {
            receitaDao.update(receita)
        }
    }

    override suspend fun delete(id: Int){
        receitaDao.delete(id)
    }

    init {
        CoroutineScope(Job()).launch {
            receitaDao.deleteAll()
            Log.i("Test", "-----------> Limpou a base de dados!")
            //delay(15000)
//            val receitas = categories()
//            for(p in receitas){
//                receitas.set(p)
//            }
            Log.i("Test", "-----------> Inseriu dados na base!")
        }

    }

    companion object {
        fun categories(): MutableList<Category> {
            val lista = mutableListOf(
                Category(
                    1,
                    "vegetariano.jpg",
                    "Vegetariano",
                    "Green"
                ),

                Category(
                    2,
                    "glutenfree.jpg",
                    "Gluten Free",
                    "Dark Blue"
                ),

                Category(
                    3,
                    "zerolactose.jpg",
                    "Zero Lactose",
                    "Purple"
                ),
            )

            return lista
        }
    }

}

