package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipe")
    fun getRecipe(
        @Query("genre_cd") genreCd: String?,
        @Query("recipe_id") recipeId: String?,
        @Query("recipe_name") recipeName: String?,
        @Query("recommended_flg") recommendedFlg: Int?
    ): Call<List<RecipeCassetteEntity>>

    @GET("recipe")
    fun getRecipeDetail(
        @Query("genre_cd") genreCd: String?,
        @Query("recipe_id") recipeId: String?,
        @Query("recipe_name") recipeName: String?,
        @Query("recommended_flg") recommendedFlg: Int?
    ): Call<List<DetailEntity>>
}