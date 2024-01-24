package com.app.bugtrackerapp.data.remote.network


import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import retrofit2.Call
import retrofit2.http.*


interface RetrofitAPIService {

//    @POST("exec?action=${params}}&sheetName=Test&='https://reqbin.com/'image&description=mydescr")
//    suspend fun addBugUser(params : AddBugRequest): String


    @FormUrlEncoded
    @POST("exec")
    suspend fun addBugUser(
        @Field("action") action: String?,
        @Field("sheetName") sheetName: String?,
        @Field("image") image: String?,
        @Field("description") description: String?,
    ): String
}