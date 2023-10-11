package com.example.stockapp.data

import kotlinx.coroutines.flow.Flow
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

}
