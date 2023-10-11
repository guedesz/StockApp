package com.example.stockapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [Product::class], version = 1)
abstract class BancoSQLite : RoomDatabase() {

    abstract fun productDao(): ProductDao
    companion object{

        @Volatile
        private var INSTANCE: BancoSQLite? = null

        fun get(context: Context): BancoSQLite {
            if (INSTANCE == null) {

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BancoSQLite::class.java,
                        "stock.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}
