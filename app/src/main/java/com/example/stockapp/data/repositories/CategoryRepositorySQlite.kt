package com.example.stockapp.data.repositories

import com.example.stockapp.data.daos.CategoryDao
import com.example.stockapp.data.objects.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryRepositorySQlite
@Inject constructor(private val categoryDao: CategoryDao)
    : CategoryRepository {

    override  val categories: Flow<List<Category>>
        get() = categoryDao.list()

    override suspend fun set(category: Category) {
        if (category.id == 0){
            categoryDao.set(category)
        } else {
            categoryDao.update(category)
        }
    }

    override suspend fun delete(id: Int){
        categoryDao.delete(id)
    }

    init {
        CoroutineScope(Job()).launch {
//            produtoDao.deleteAll()
//            Log.i("Test", "-----------> Limpou a base de dados!")
//            //delay(15000)
//            val products = products()
//            for(p in products){
//                produtoDao.set(p)
//            }
//            Log.i("Test", "-----------> Inseriu dados na base!")
        }

    }
}

