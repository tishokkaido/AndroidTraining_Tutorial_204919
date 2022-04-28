package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MainApiService {
    // TODO パスを記載
    @GET("recipe")
    // @Queryでパラメータを指定（今回は全件持ってくる）
    fun getRecipe(
        @Query("genre_cd") genreCd: String?,
        @Query("recipe_id") recipeId: Int?,
        @Query("recipe_name") recipeName: String?,
        @Query("recommended_flg") recommendedFlg: Int?
    ): Call<List<RecipeCassetteEntity>>
}