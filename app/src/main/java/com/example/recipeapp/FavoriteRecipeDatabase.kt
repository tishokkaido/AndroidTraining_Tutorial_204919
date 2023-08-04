package com.example.recipeapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteRecipe::class], version = 1)
abstract class FavoriteRecipeDatabase: RoomDatabase() {
    abstract fun favoriteRecipeDao(): FavoriteRecipeDao
}