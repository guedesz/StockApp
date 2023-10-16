package com.example.stockapp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepositorySQLite
@Inject constructor(private val produtoDao: ProductDao)
    : ProductRepository {

    override  val products: Flow<List<Product>>
        get() = produtoDao.list()

    override suspend fun set(product: Product) {
        if (product.id == 0){
            produtoDao.set(product)
        } else {
            produtoDao.update(product)
        }
    }

    override suspend fun delete(id: Int){
        produtoDao.delete(id)
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

