package com.hendry.testegd.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesQuotaDao {

    @Insert
    suspend fun insertQuote(quote: FavoritesQuote)

    @Query("SELECT * FROM favorite_quotes ORDER BY saved_at DESC")
    fun getAllQuotes(): Flow<List<FavoritesQuote>>

    @Delete
    suspend fun deleteQuote(quote: FavoritesQuote)
}