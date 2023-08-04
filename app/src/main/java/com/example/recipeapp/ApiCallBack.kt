package com.example.recipeapp

interface ApiCallBack {
    fun onSuccess(result: List<RecipeCassetteEntity>)
    fun onFailure(t: Throwable)
}