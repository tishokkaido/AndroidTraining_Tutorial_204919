package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailActivity: AppCompatActivity(), DetailApiCallBack, CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var favoriteRecipe: FavoriteRecipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }?: IllegalAccessException("Toolbar cannot be null")

        // Main画面からのrecipe_idの受け取り
        var recipeId: String? = null
        if(intent != null) {
            recipeId = intent.getStringExtra("recipe_id")!!
        }

        var existsCheck =
//            TODO 本当はここで存在しないIDが渡された時の処理も行わなければならない？
            runBlocking {
                FavoriteRepository().exists(applicationContext, recipeId!!)
            }

        // recipeIdがテーブルに存在する場合の処理
        val favoriteButton = findViewById<ImageButton>(R.id.favorite_button)
        if (existsCheck) {
            favoriteButton.setImageResource(R.drawable.ic_star_orange)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_star_border_orange)
        }

        // Repositoryを呼び出す。
        val repository = DetailApiRepository()
        repository.fetchRecipeDetail(this, recipeId!!)

//        レコードの存在確認後処理が行われるように設定
        // お気に入りボタン押下時
        findViewById<ImageButton>(R.id.favorite_button).setOnClickListener {
            existsCheck =
                runBlocking {
                    FavoriteRepository().exists(applicationContext, recipeId)
                }
            if (!existsCheck) {
                runBlocking {
                    FavoriteRepository().save(applicationContext, favoriteRecipe)
                }
                Snackbar.make(it, "お気に入りに登録しました", Snackbar.LENGTH_SHORT).show()
                favoriteButton.setImageResource(R.drawable.ic_star_orange)
            } else {
                runBlocking {
                    FavoriteRepository().delete(applicationContext, favoriteRecipe)
                }
                Snackbar.make(it, "お気に入りから削除しました", Snackbar.LENGTH_SHORT).show()
                favoriteButton.setImageResource(R.drawable.ic_star_border_orange)
            }
        }
    }

    /**
     * 通信成功時
     */
    override fun onSuccess(result: List<DetailEntity>) {
        Log.d("ApiDetail", "$result")

        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        toolbar.title = result[0].recipeName
        setSupportActionBar(toolbar)

        val recipeImage = findViewById<ImageView>(R.id.detail_image)
        Picasso.get()
            .load(result[0].imageUrl)
            .error(R.drawable.ic_no_image)
            .into(recipeImage)

        // レシピの説明
        findViewById<TextView>(R.id.detail_info).text = result[0].introduction

        // 材料を表示するリサイクラービュー
        val ingredientsAdapter = IngredientsRecyclerAdapter(result[0].cookingIngredients!!)
        val ingredientsRecyclerView = findViewById<RecyclerView>(R.id.detail_ingredients_recyclerView)
        ingredientsRecyclerView.adapter = ingredientsAdapter

        // 手順を表示するリサイクラービュー
        val procedureAdapter = ProcedureRecyclerAdapter(result[0].cookingMethod!!)
        val procedureRecyclerView = findViewById<RecyclerView>(R.id.detail_procedure_recyclerView)
        procedureRecyclerView.adapter = procedureAdapter

        favoriteRecipe =
            FavoriteRecipe(
                result[0].recipeId!!,
                result[0].imageUrl!!,
                result[0].recommendedFlg,
                result[0].recipeName!!,
                result[0].introduction!!
            )
    }

    /**
     * 通信失敗時
     */
    override fun onFailure(t: Throwable) {
        Log.d("ApiDetail", "$t")
        val layout: FrameLayout? = findViewById(R.id.detail_view)
        layout?.removeAllViews()
        layoutInflater.inflate(R.layout.network_error, layout)

        findViewById<RelativeLayout>(R.id.network_error_view).setOnClickListener {
            reload()
        }
    }

    /**
     * 戻るボタンタップ時
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Activityのリロード
     */
    private fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()

        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}