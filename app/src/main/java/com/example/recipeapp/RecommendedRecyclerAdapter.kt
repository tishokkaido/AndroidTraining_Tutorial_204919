package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecommendedRecyclerAdapter(
    private val listenerRecommended: OnRecommendedItemClickListener,
    recipeList: List<RecipeCassetteEntity>
): RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder>() {
    private val viewModels = recipeList.map { ViewModel(it.recipeId!!, it.imageUrl!!, it.recommendedFlg, it.recipeName!!, it.introduction!!) }

    override fun getItemCount(): Int {
        return viewModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // アイテムのViewを生成
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_cassette_item, parent, false)
        // ViewHolderを生成
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // データをviewHolderに紐づける
        holder.bind(viewModel = viewModels[position], position, listenerRecommended)
    }

    interface OnRecommendedItemClickListener {
        fun onRecommendedItemClick(id: String, position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val view = itemView
        fun bind(viewModel: ViewModel, position: Int, listenerRecommended: OnRecommendedItemClickListener) {
            view.rootView.setOnClickListener {
                listenerRecommended.onRecommendedItemClick(viewModel.recipeId, position)
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
    data class ViewModel(
        val recipeId: String,
        val imageUrl: String,
        val recommendedFlg: Int,
        val recipeName: String,
        val introduction: String
    )
}