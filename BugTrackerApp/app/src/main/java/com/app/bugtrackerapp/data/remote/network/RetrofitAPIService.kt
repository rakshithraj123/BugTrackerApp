package com.app.bugtrackerapp.data.remote.network


import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import com.app.bugtrackerapp.model.Bug
import retrofit2.http.*


interface RetrofitAPIService {

    @POST("posts")
    suspend fun addBugUser(@Body  params : AddBugRequest): Bug
}