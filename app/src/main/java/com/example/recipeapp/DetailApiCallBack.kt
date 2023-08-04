package com.example.recipeapp

interface DetailApiCallBack {
    fun onSuccess(result: List<DetailEntity>)
    fun onFailure(t: Throwable)
}