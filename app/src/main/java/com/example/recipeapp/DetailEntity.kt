package com.example.recipeapp

import com.google.gson.annotations.SerializedName

class DetailEntity {
    @SerializedName("cooking_ingredients")
    var cookingIngredients: List<Ingredients>? = null
    @SerializedName("cooking_method")
    var cookingMethod: List<Procedures>? = null
    @SerializedName("recipe_id")
    var recipeId: String? = null
    @SerializedName("recipe_name")
    var recipeName: String? = null
    @SerializedName("introduction")
    var introduction: String? = null
    @SerializedName("main_gazo")
    var imageUrl: String? = null
    @SerializedName("recommended_flg")
    var recommendedFlg: Int = 0
}

class Ingredients{
    @SerializedName("material")
    var material: String? = null
    @SerializedName("quantity")
    var quantity: String? = null
}

class Procedures {
    @SerializedName("procedure_no")
    var procedureNo: Int? = null
    @SerializedName("procedure")
    var procedure: String? = null
}