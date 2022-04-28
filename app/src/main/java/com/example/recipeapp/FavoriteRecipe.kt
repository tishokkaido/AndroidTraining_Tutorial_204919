package com.example.recipeapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteRecipe(
    @PrimaryKey
    val recipe_id: String,
    @ColumnInfo
    val recipeImageUrl: String,
    @ColumnInfo
    val recommendedFlg: Int,
    @ColumnInfo
    val recipeName: String,
    @ColumnInfo
    val introduction: String
)
