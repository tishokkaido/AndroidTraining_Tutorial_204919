package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProcedureRecyclerAdapter(private val procedureList: List<Procedures>): RecyclerView.Adapter<ProcedureRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val procedureNo: TextView = view.findViewById(R.id.procedure_no)
        val procedure: TextView = view.findViewById(R.id.procedure)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.procedure_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.procedureNo.text = procedureList[position].procedureNo.toString()
        holder.procedure.text = procedureList[position].procedure
    }

    override fun getItemCount(): Int {
        return procedureList.size
    }
}