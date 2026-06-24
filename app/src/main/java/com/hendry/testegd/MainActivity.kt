package com.hendry.testegd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hendry.testegd.viewmodel.QuoteUiState
import com.hendry.testegd.viewmodel.QuoteViewModel
import android.os.Handler
import android.os.Looper
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var textViewQuote: TextView
    private lateinit var textViewAuthor: TextView
    private lateinit var buttonRefresh: Button
    private lateinit var buttonFavorite: Button
    private lateinit var buttonViewFavorites: Button
    private lateinit var circleProgressBar: ProgressBar

    private val quoteViewModel: QuoteViewModel by viewModels()

    private lateinit var chipNormal: Chip
    private lateinit var chipLoading: Chip
    private lateinit var chipError: Chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Quote of The Day"

        textViewQuote = findViewById(R.id.quote)
        textViewAuthor = findViewById(R.id.author)
        buttonRefresh = findViewById(R.id.refreshQuote)
        buttonFavorite = findViewById(R.id.favorite)
        buttonViewFavorites = findViewById(R.id.viewFavorites)
        circleProgressBar = findViewById(R.id.circleProgressbar)

        chipNormal = findViewById(R.id.chipNormal)
        chipLoading = findViewById(R.id.chipLoading)
        chipError = findViewById(R.id.chipError)

        observeViewModel()
        btnListener()

        quoteViewModel.loadQuote()
    }

    private fun btnListener() {
        buttonRefresh.setOnClickListener {
            quoteViewModel.loadQuote()
        }

        buttonFavorite.setOnClickListener {
            quoteViewModel.saveFavoriteQuote()
        }

        buttonViewFavorites.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        chipNormal.setOnClickListener {
            quoteViewModel.loadQuote()
        }

        chipLoading.setOnClickListener {
            setLoading(true)

            Handler(Looper.getMainLooper()).postDelayed({
                setLoading(false)
            }, 1500)
        }

        chipError.setOnClickListener {
            setLoading(false)

            Toast.makeText(
                this,
                "Failed to load quote. Try again.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun setLoading(isLoading: Boolean) {
        circleProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        buttonRefresh.isEnabled = !isLoading
        buttonFavorite.isEnabled = !isLoading
        buttonViewFavorites.isEnabled = !isLoading

        chipNormal.isEnabled = !isLoading
        chipLoading.isEnabled = !isLoading
        chipError.isEnabled = !isLoading
    }

    private fun observeViewModel() {
        quoteViewModel.quoteState.observe(this) { state ->
            when (state) {
                is QuoteUiState.Loading -> {
                    circleProgressBar.visibility = View.VISIBLE
                }

                is QuoteUiState.Success -> {
                    circleProgressBar.visibility = View.GONE

                    textViewQuote.text = "\"${state.quoteBody}\""
                    textViewAuthor.text = "- ${state.quoteAuthor}"
                }

                is QuoteUiState.Error -> {
                    circleProgressBar.visibility = View.GONE

                    Toast.makeText(
                        this,
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        quoteViewModel.favoriteMessage.observe(this) { message ->
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}