package com.example.recipeapp

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository {
    suspend fun save(applicationContext: Context, favoriteRecipe: FavoriteRecipe) {
        withContext(Dispatchers.IO) {
            val db = getInstance(applicationContext)
            db.favoriteRecipeDao().insert(favoriteRecipe)
        }
    }

    suspend fun delete(applicationContext: Context, favoriteRecipe: FavoriteRecipe) {
        withContext(Dispatchers.IO) {
            val db = getInstance(applicationContext)
            db.favoriteRecipeDao().delete(favoriteRecipe)
        }
    }

    suspend fun exists(applicationContext: Context, recipeId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val db = getInstance(applicationContext)
            db.favoriteRecipeDao().exists(recipeId)
        }
    }

    suspend fun loadAll(applicationContext: Context): List<FavoriteRecipe> {
        return withContext(Dispatchers.IO) {
            val db = getInstance(applicationContext)
            db.favoriteRecipeDao().getAll()
        }
    }

    companion object {
        fun getInstance(context: Context): RecipeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RecipeDatabase::class.java,
                "recipe_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}