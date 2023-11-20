package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Receita
import kotlinx.coroutines.flow.Flow

interface ReceitaRepository {

    val receitas: Flow<List<Receita>>
    suspend fun set(receita: Receita)
    suspend fun delete(receita: Receita)
    suspend fun getReceitas(): List<Receita>
}
