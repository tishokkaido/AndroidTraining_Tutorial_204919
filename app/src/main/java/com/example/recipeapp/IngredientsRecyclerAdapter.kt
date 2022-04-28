package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IngredientsRecyclerAdapter(private val ingredientsList: List<Ingredients>): RecyclerView.Adapter<IngredientsRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ingredient: TextView = view.findViewById(R.id.ingredient)
        val quantity: TextView = view.findViewById(R.id.quantity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.ingradients_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredient.text = ingredientsList[position].material
        holder.quantity.text = ingredientsList[position].quantity
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
}