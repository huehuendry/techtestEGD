package com.hendry.testegd.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_quotes")

data class FavoritesQuote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "quote_body")
    val quoteBody: String,

    @ColumnInfo(name = "quote_author")
    val quoteAuthor: String,

    @ColumnInfo(name = "saved_at")
    val savedAt: Long = System.currentTimeMillis()
)