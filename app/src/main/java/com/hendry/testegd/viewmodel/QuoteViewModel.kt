package com.hendry.testegd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hendry.testegd.data.FavoritesQuote
import com.hendry.testegd.data.FavoritesQuoteDatabase
import com.hendry.testegd.service.ApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class QuoteUiState {
    object Loading : QuoteUiState()
    data class Success(
        val quoteBody: String,
        val quoteAuthor: String
    ) : QuoteUiState()

    data class Error(
        val message: String
    ) : QuoteUiState()
}

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val database = FavoritesQuoteDatabase.getDatabase(application)
    private val apiCall = ApiCall()

    private val _quoteState = MutableLiveData<QuoteUiState>()
    val quoteState: LiveData<QuoteUiState> = _quoteState

    private val _favoriteMessage = MutableLiveData<String>()
    val favoriteMessage: LiveData<String> = _favoriteMessage

    private var currentQuoteBody: String = ""
    private var currentQuoteAuthor: String = ""

    fun loadQuote() {
        _quoteState.value = QuoteUiState.Loading

        apiCall.getQuotes(
            onSuccess = { response ->
                val quoteBody = response.quote?.body.orEmpty()
                val quoteAuthor = response.quote?.author.orEmpty()

                if (quoteBody.isBlank()) {
                    _quoteState.value = QuoteUiState.Error("Quote tidak tersedia")
                    return@getQuotes
                }

                currentQuoteBody = quoteBody
                currentQuoteAuthor = quoteAuthor.ifBlank { "Unknown" }

                _quoteState.value = QuoteUiState.Success(
                    quoteBody = currentQuoteBody,
                    quoteAuthor = currentQuoteAuthor
                )
            },
            onError = { message ->
                _quoteState.value = QuoteUiState.Error(message)
            }
        )
    }

    fun saveFavoriteQuote() {
        if (currentQuoteBody.isBlank()) {
            _favoriteMessage.value = "Quote belum tersedia"
            return
        }

        val favoriteQuote = FavoritesQuote(
            quoteBody = currentQuoteBody,
            quoteAuthor = currentQuoteAuthor.ifBlank { "Unknown" }
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.favoriteQuoteDao().insertQuote(favoriteQuote)
            }

            _favoriteMessage.value = "Quote added to favorites"
        }
    }
}