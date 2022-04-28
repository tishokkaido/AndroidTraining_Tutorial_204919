package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FavoriteRecyclerAdapter(
    private val listenerFavorite: OnFavoriteItemClickListener,
    recipeList: List<FavoriteRecipe>
): RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>() {

    private val viewModels = recipeList.map { ViewModel(it.recipe_id, it.recipeImageUrl, it.recommendedFlg, it.recipeName, it.introduction) }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val view = itemView
        fun bind(viewModel: ViewModel, position: Int, listenerFavorite: OnFavoriteItemClickListener) {
            view.rootView.setOnClickListener {
                listenerFavorite.onFavoriteItemClick(viewModel.recipeId, position)
            }
            val recipeImage = view.findViewById<ImageView>(R.id.recipe_image)
            Picasso.get()
                .load(viewModel.imageUrl)
                .error(R.drawable.ic_no_image)
                .into(recipeImage)
            view.findViewById<TextView>(R.id.recommended_flg).visibility =
                if (viewModel.recommendedFlg == 1) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            view.findViewById<TextView>(R.id.recipe_name).text = viewModel.recipeName
            view.findViewById<TextView>(R.id.introduction).text = viewModel.introduction
        }
    }

    override fun getItemCount(): Int {
        return viewModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_cassette_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel = viewModels[position], position = position, listenerFavorite)
    }

    interface OnFavoriteItemClickListener {
        fun onFavoriteItemClick(id: String, position: Int)
    }

    data class ViewModel(
        val recipeId: String,
        val imageUrl: String,
        val recommendedFlg: Int,
        val recipeName: String,
        val introduction: String
    )
}