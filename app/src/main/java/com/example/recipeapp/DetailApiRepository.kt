package com.example.recipeapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

class DetailApiRepository {
    fun fetchRecipeDetail(listener: DetailApiCallBack, recipeId: String) {
        ApiClient.newInstance().service.getRecipeDetail(genreCd = null, recipeId = recipeId, recipeName = null, recommendedFlg = null)
            .enqueue(object : Callback<List<DetailEntity>> {
                override fun onResponse(
                    call: Call<List<DetailEntity>>,
                    response: Response<List<DetailEntity>>
                ) {
                    if (response.isSuccessful) {
                        listener.onSuccess(response.body().orEmpty())
                    } else {
                        onFailure(call, IllegalStateException("Api Access Failure"))
                    }
                }

                override fun onFailure(call: Call<List<DetailEntity>>, t: Throwable) {
                    listener.onFailure(t)
                }
            })
    }
}