package com.example.recipeapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

class RecommendedRepository {
    fun fetchRecipe(listener: MainApiCallBack) {
        ApiClient.newInstance().service.getRecipe(genreCd = null, recipeId = null, recipeName = null, recommendedFlg = null)
            .enqueue(object : Callback<List<RecipeCassetteEntity>> {
                override fun onResponse(
                    call: Call<List<RecipeCassetteEntity>>,
                    response: Response<List<RecipeCassetteEntity>>
                ) {
                    if (response.isSuccessful) {
                        listener.onSuccess(response.body().orEmpty())
                    } else {
                        onFailure(call, IllegalStateException("Api Access Failure"))
                    }
                }

                override fun onFailure(call: Call<List<RecipeCassetteEntity>>, t: Throwable) {
                    listener.onFailure(t)
                }
            })
    }
}