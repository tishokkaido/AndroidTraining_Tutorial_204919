package com.example.recipeapp

interface MainApiCallBack {
    fun onSuccess(result: List<RecipeCassetteEntity>)
    fun onFailure(t: Throwable)
}