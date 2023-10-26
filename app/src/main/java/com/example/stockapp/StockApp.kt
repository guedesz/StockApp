package com.example.stockapp

import android.app.Application

import android.content.Context
import com.example.stockapp.data.BancoSQLite
import com.example.stockapp.data.daos.CategoryDao
import com.example.stockapp.data.daos.ReceitaDao
import com.example.stockapp.data.repositories.CategoryRepository
import com.example.stockapp.data.repositories.CategoryRepositorySQlite
import com.example.stockapp.data.repositories.ReceitaRepository
import com.example.stockapp.data.repositories.ReceitaRepositorySQlite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
@Module
@InstallIn(SingletonComponent::class)

class StockApp : Application() {

    @Provides
    fun provideReceitaRepository(receitaDao: ReceitaDao) : ReceitaRepository {
        return ReceitaRepositorySQlite(receitaDao)
    }

    @Provides
    fun provideReceitaDao(banco: BancoSQLite) : ReceitaDao {
        return banco.receitaDao()
    }

    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao) : CategoryRepository {
        return CategoryRepositorySQlite(categoryDao)
    }

    @Provides
    fun provideCategoryDao(banco: BancoSQLite) : CategoryDao {
        return banco.categoryDao()
    }
    
    @Provides
    @Singleton
    fun provideBanco(@ApplicationContext ctx: Context) : BancoSQLite{
        return BancoSQLite.get(ctx)
    }

}