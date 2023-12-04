package com.example.roomwordapp.ui.home
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordapp.R
import com.example.roomwordapp.ui.expense.CreateExpenseActivity
import com.google.android.material.card.MaterialCardView

data class CardData(val title: String, val description: String, val destination: Intent)

class CardAdapter(private val cardList: List<CardData>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val cardContainer: MaterialCardView = itemView.findViewById(R.id.cardContainer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_card_home, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.textTitle.text = card.title
        holder.textDescription.text = card.description
        val cardContainer = holder.cardContainer
        holder.cardContainer.setOnClickListener {
            startActivity(cardContainer.context, cardList[position].destination, null)
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}