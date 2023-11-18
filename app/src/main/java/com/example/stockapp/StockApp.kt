package com.example.stockapp

import android.app.Application

import android.content.Context
import com.example.stockapp.data.BancoSQLite
import com.example.stockapp.data.daos.CategoryDao
import com.example.stockapp.data.daos.ReceitaDao
import com.example.stockapp.data.repositories.CategoryRepository
import com.example.stockapp.data.repositories.CategoryRepositoryFirebase
import com.example.stockapp.data.repositories.CategoryRepositorySQlite
import com.example.stockapp.data.repositories.ReceitaRepository
import com.example.stockapp.data.repositories.ReceitaRepositoryFirebase
import com.example.stockapp.data.repositories.ReceitaRepositorySQlite
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@HiltAndroidApp
@Module
@InstallIn(SingletonComponent::class)
class StockApp : Application() {

    @Provides
    fun provideReceitaRepository(receitaDao: ReceitaDao) : ReceitaRepositorySQlite {
        return ReceitaRepositorySQlite(receitaDao)
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao) : CategoryRepositorySQlite {
        return CategoryRepositorySQlite(categoryDao)
    }

    @Provides
    fun provideReceitaDao(banco: BancoSQLite) : ReceitaDao {
        return banco.receitaDao()
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

    //Fire base
    @Provides
    @Singleton
    @Named("categories")
    fun provideCategoriesRef(): CollectionReference {
        return Firebase.firestore.collection("categories")
    }

    @Provides
    @Singleton
    @Named("receitas")
    fun provideReceitasRef(): CollectionReference {
        return Firebase.firestore.collection("receitas")
    }

    @Provides
    fun provideCategoryRepositoryFirebase(@Named("categories") collectionRef: CollectionReference): CategoryRepository {
        return CategoryRepositoryFirebase(collectionRef)
    }

    @Provides
    fun provideReceitasRepositoryFirebase(@Named("receitas") collectionRef: CollectionReference): ReceitaRepository {
        return ReceitaRepositoryFirebase(collectionRef)
    }

}