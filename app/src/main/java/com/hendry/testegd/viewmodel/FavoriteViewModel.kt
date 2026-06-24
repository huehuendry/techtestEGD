package com.hendry.testegd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hendry.testegd.data.FavoritesQuote
import com.hendry.testegd.data.FavoritesQuoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val database = FavoritesQuoteDatabase.getDatabase(application)
    private val favoriteQuoteDao = database.favoriteQuoteDao()

    val favoriteQuotes: LiveData<List<FavoritesQuote>> =
        favoriteQuoteDao.getAllQuotes().asLiveData()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun deleteQuote(quote: FavoritesQuote) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favoriteQuoteDao.deleteQuote(quote)
            }

            _message.value = "Quote deleted"
        }
    }
}