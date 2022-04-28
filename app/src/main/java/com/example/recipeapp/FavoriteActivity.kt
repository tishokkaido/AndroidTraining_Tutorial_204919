package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.runBlocking

class FavoriteActivity: AppCompatActivity(), FavoriteRecyclerAdapter.OnFavoriteItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // Navigationのハンバーガアイコンのセット
        val toolbar = findViewById<Toolbar>(R.id.activity_favorite_toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.favorite_drawer)
        setToggle(this, drawerLayout, toolbar)

        val navigationDrawer = findViewById<NavigationView>(R.id.main_drawer_navigation)
        onNavigation(applicationContext, navigationDrawer, drawerLayout)

        // DBからリストを取得
        val favoriteRecipeList =
            runBlocking {
                FavoriteRepository().loadAll(applicationContext)
            }

        if (favoriteRecipeList.isNotEmpty()) {
            // アダプターへリストを渡し、RecyclerViewでリスト表示を行う。
            val adapter = FavoriteRecyclerAdapter(this, favoriteRecipeList)
            findViewById<RecyclerView>(R.id.activity_favorite_recyclerView).adapter = adapter
        } else {
            // Layoutを取り除き、エラー時のレイアウトを表示する。
            val layout: LinearLayout? = findViewById(R.id.favorite_view)
            layout?.removeAllViews()
            layoutInflater.inflate(R.layout.no_favorite_error, layout)
        }
    }

    /**
     * カセットタップ押下時の処理
      */
    override fun onFavoriteItemClick(id: String, position: Int) {
        val intent = Intent(applicationContext, DetailActivity::class.java).also {
            it.putExtra("recipe_id", id)
        }
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        // Activityのリロード
        reload()
        Log.d("FavoriteActivity", "onRestart")
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