package com.example.recipeapp

import com.google.gson.annotations.SerializedName

class RecipeCassetteEntity {
    @SerializedName("recipe_id")
    var recipeId: String? = null

    @SerializedName("main_gazo")
    var imageUrl: String? = null

    @SerializedName("recommended_flg")
    var recommendedFlg: Int = 0

    @SerializedName("recipe_name")
    var recipeName: String? = null

    @SerializedName("introduction")
    var introduction: String? = null
}