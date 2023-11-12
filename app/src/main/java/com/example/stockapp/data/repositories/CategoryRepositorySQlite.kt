package com.example.stockapp.data.repositories

import android.util.Log
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

   override suspend fun getCategoryByName(name: String): Category {
        val info = categoryDao.getCategoryByName(name)
        return Category(
            info.id,
            info.docId,
            info.photo,
            info.name,
            info.color
        )
    }

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

}

