package com.example.recipeapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteRecipeDao {
    @Query("SELECT * FROM FavoriteRecipe")
    fun getAll(): List<FavoriteRecipe>

    @Query("SELECT * FROM FavoriteRecipe WHERE recipe_id == :recipeId")
    fun getRecipeById(recipeId: Int): FavoriteRecipe

    @Insert
    fun insert(favoriteRecipe: FavoriteRecipe)

    @Delete
    fun delete(favoriteRecipe: FavoriteRecipe)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteRecipe WHERE recipe_id == :recipeId)")
    fun exists(recipeId: String): Boolean

}