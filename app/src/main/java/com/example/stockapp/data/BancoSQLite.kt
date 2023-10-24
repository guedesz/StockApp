package com.example.stockapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stockapp.data.daos.CategoryDao
import com.example.stockapp.data.objects.Category
import kotlin.concurrent.Volatile

@Database(entities = [Product::class, Category::class], version = 2)
abstract class BancoSQLite : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    companion object{

        @Volatile
        private var INSTANCE: BancoSQLite? = null

        fun get(context: Context): BancoSQLite {
            if (INSTANCE == null) {

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BancoSQLite::class.java,
                        "banco.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}
