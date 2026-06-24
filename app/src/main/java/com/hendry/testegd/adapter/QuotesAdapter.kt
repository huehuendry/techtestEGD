package com.hendry.testegd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hendry.testegd.R
import com.hendry.testegd.data.FavoritesQuote

class QuotesAdapter(
    private val dataSet: ArrayList<FavoritesQuote>,
    private val onDeleteClick: (FavoritesQuote) -> Unit
) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rvQuote: TextView = itemView.findViewById(R.id.rvQuote)
        val rvAuthor: TextView = itemView.findViewById(R.id.rvAuthor)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_quote, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        viewHolder.rvQuote.text = "\"${item.quoteBody}\""
        viewHolder.rvAuthor.text = "- ${item.quoteAuthor}"

        viewHolder.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = dataSet.size

    fun setData(newData: List<FavoritesQuote>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }
}