package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PagerFragment: Fragment(), MainApiCallBack, RecommendedRecyclerAdapter.OnRecommendedItemClickListener, FavoriteRecyclerAdapter.OnFavoriteItemClickListener, CoroutineScope {

    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    /**
     * Fragmentをレイアウトに反映
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        job = SupervisorJob()

        val layoutId = arguments?.getInt(KEY)

        // RecommendedFragmentへのアダプターのセット
        val recommendedFragment = inflater.inflate(R.layout.fragment_recommend, container, false)
        val mainRecommendedRepository = RecommendedRepository()
        mainRecommendedRepository.fetchRecipe(this)

        val favoriteFragment = inflater.inflate(R.layout.fragment_favorite, container, false)
        // DBからのデータのロード
        val favoriteRecipeList =
            runBlocking {
                FavoriteRepository().loadAll(requireContext())
            }
        Log.d("DB Check", "$favoriteRecipeList")
        if (favoriteRecipeList.isNotEmpty()) {
            val favoriteRecyclerViewAdapter = FavoriteRecyclerAdapter(listenerFavorite = this, favoriteRecipeList)
            favoriteFragment.findViewById<RecyclerView>(R.id.favorite_recyclerView).adapter = favoriteRecyclerViewAdapter
            Log.d("DB Check", "Success")
        } else {
            val layout: LinearLayout? = favoriteFragment.findViewById(R.id.favorite_view)
            layout?.removeAllViews()
            layoutInflater.inflate(R.layout.no_favorite_error, layout)
            Log.d("DB Check", "Exception")
        }

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
        val adapter = RecommendedRecyclerAdapter(this, result)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recommended_recyclerView)
        recyclerView?.adapter = adapter
    }

    /**
     * Api通信に失敗した場合
     */
    override fun onFailure(t: Throwable) {
        Log.d("ApiCheck", "$t")
        val layout: LinearLayout? = view?.findViewById(R.id.recommended_view)
        layout?.removeAllViews()
        layoutInflater.inflate(R.layout.network_error, layout)
        view?.findViewById<RelativeLayout>(R.id.network_error_view)?.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * RecommendedのRecyclerViewのクリック処理
     */
    override fun onRecommendedItemClick(id: String, position: Int) {
        val intent = Intent(context, DetailActivity::class.java).also {
            it.putExtra("recipe_id", id)
        }
        startActivity(intent)
    }

    /**
     * FavoriteのRecyclerViewのクリック処理
     */
    override fun onFavoriteItemClick(id: String, position: Int) {
        val intent = Intent(context, DetailActivity::class.java).also {
            it.putExtra("recipe_id", id)
        }
        startActivity(intent)
    }

    companion object {
        private const val KEY = "hogehoge"
        fun newInstance(layoutId: Int): PagerFragment {
            val bundle = Bundle().also {
                it.putInt(KEY, layoutId)
            }
            return PagerFragment().also {
                it.arguments = bundle
            }
        }
    }
}