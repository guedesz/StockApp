package com.example.stockapp.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepositorySQLite
@Inject constructor(val produtoDao: ProductDao)
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

    companion object {
        fun products(): MutableList<Product> {
            val list = mutableListOf(
                Product(
                    1,
                    "Acompanhamentos",
                    "Manteiga, nata, mel, requeijão salgado, queijo branco.",
                    27.56,
                    160,
                    "cafe1.jpg",
                ),
                Product(
                    2,
                    "Bruschetta de salmão e abacate",
                    "Pão preto, salmão defumado, queijo Philadelphia, abacate, tomate, ovos.",
                    52.49,
                    180,
                    "cafe2.jpg",
                ),
                Product(
                    3,
                    "Croque Madame",
                    "Pão torrado, salame de vitela, ovos, manteiga, queijo, rúcula, cenoura, pepino, rabanete.",
                    35.1,
                    125,
                    "cafe3.jpg",
                ),
                Product(
                    4,
                    "Panquecas de queijo",
                    "Folhada, recheio de queijo, rúcula, cenoura, pepino, rabanete.",
                    32.34,
                    110,
                    "cafe4.jpg",
                ),
                Product(
                    5,
                    "Sanduíche de queijo",
                    "Pão torrado, queijo cheddar, queijo gouda, manteiga, rúcula, cenoura, pepino, rabanete.",
                    22.34,
                    110,
                    "cafe5.jpg",
                ),
                Product(
                    6,
                    "Sanduíche de torrada escura",
                    "Pão escuro, presunto, ovos, queijo gouda, rúcula, cenoura, pepino, rabanete.",
                    28.14,
                    170,
                    "cafe6.jpg",
                ),
                Product(
                    7,
                    "Omelete misto",
                    "Salada de ovos, filé de frango, queijo gouda, rúcula e tomate.",
                    25.1,
                    170,
                    "cafe7.jpg",
                ),
                Product(
                    8,
                    "Prato de queijo",
                    "Queijo de leite de ovelha, queijo motal, requeijão, queijo branco.",
                    20.3,
                    170,
                    "aperitivo1.jpg",
                ),
            )

            return list
        }
    }
    }
