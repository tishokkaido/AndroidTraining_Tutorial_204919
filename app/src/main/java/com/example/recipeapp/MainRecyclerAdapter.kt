package com.example.recipeapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainRecyclerAdapter(
    private val listener: OnItemClickListener,
    labelList: List<RecipeCassetteEntity>
): RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    private val viewModels = labelList.map { ViewModel(it.recipeId, it.imageUrl, it.recommendedFlg, it.recipeName, it.introduction) }

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
        holder.bind(viewModel = viewModels[position], position, listener)
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int?, position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val view = itemView
        fun bind(viewModel: ViewModel, position: Int, listener: OnItemClickListener) {
            view.rootView.setOnClickListener {
                listener.onItemClick(viewModel.recipeId, position)
            }
            view.findViewById<ImageView>(R.id.recipe_image)// TODO 画像のパースと追加。
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
        val recipeId: Int?,
        val imageUrl: String?,
        val recommendedFlg: Int,
        val recipeName: String?,
        val introduction: String?
    )
}