package com.example.stockapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stockapp.data.daos.CategoryDao
import com.example.stockapp.data.daos.ReceitaDao
import com.example.stockapp.data.objects.Category
import com.example.stockapp.data.objects.Receita
import kotlin.concurrent.Volatile

@Database(entities = [Category::class, Receita::class], version = 6)
abstract class BancoSQLite : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract  fun receitaDao(): ReceitaDao
    companion object{

        @Volatile
        private var INSTANCE: BancoSQLite? = null

        fun get(context: Context): BancoSQLite {

            if (INSTANCE == null) {

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BancoSQLite::class.java,
                        "banco13.db"
                    ).build()
                }
            }

            return INSTANCE!!
        }

    }
}
