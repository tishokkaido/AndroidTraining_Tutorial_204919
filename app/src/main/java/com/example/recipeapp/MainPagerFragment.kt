package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MainPagerFragment: Fragment(), MainApiCallBack, MainRecyclerAdapter.OnItemClickListener {
    /**
     * Fragmentをレイアウトに反映
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = arguments?.getInt(KEY)

        // RecommendedFragmentへのアダプターのセット
        val recommendedFragment = inflater.inflate(R.layout.fragment_recommend, container, false)
        val mainRecommendedRepository = MainRecommendedRepository()
        mainRecommendedRepository.fetchRecipe(this)

        // FavoriteFragmentへのアダプターのセット
        val favoriteFragment = inflater.inflate(R.layout.fragment_favorite, container, false)
        // TODO adapterにリストを渡し、レイアウトのrecyclerViewにセットする

        return layoutId?.let {
            // 表示するレイアウトの設定
            if (layoutId == R.layout.fragment_recommend) {
                recommendedFragment
            } else {
                favoriteFragment
            }
        } ?:super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * Api通信に成功した場合
     */
    override fun onSuccess(result: List<RecipeCassetteEntity>) {
        Log.d("Api", "$result")
        val adapter = MainRecyclerAdapter(this, result)
        view?.findViewById<RecyclerView>(R.id.recommended_recyclerView)?.adapter = adapter
    }

    /**
     * Api通信に失敗した場合
     */
    override fun onFailure(t: Throwable) {
        Log.d("ApiCheck", "$t")
        val layout: LinearLayout? = view?.findViewById(R.id.recommended_view)
        layout?.removeAllViews()
        layoutInflater.inflate(R.layout.network_error, layout)
    }

    /**
     * RecyclerViewのクリック処理
     */
    override fun onItemClick(id: Int?, position: Int) {
        val intent = Intent(context, DetailActivity::class.java).also {
            it.putExtra("recipe_id", id)
        }
        startActivity(intent)
    }

    companion object {
        private const val KEY = "hogehoge"
        fun newInstance(layoutId: Int): MainPagerFragment {
            val bundle = Bundle().also {
                it.putInt(KEY, layoutId)
            }
            return MainPagerFragment().also {
                it.arguments = bundle
            }
        }
    }
}