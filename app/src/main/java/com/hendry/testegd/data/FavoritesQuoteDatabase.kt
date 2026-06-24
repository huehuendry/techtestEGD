package com.hendry.testegd.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritesQuote::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesQuoteDatabase : RoomDatabase() {

    abstract fun favoriteQuoteDao(): FavoritesQuotaDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesQuoteDatabase? = null

        fun getDatabase(context: Context): FavoritesQuoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesQuoteDatabase::class.java,
                    "quote_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}