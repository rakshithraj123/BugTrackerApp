package com.app.bugtrackerapp.data.remote

import com.app.bugtrackerapp.data.remote.network.RetrofitAPIService
import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val retrofitAPIService: RetrofitAPIService)  :
    NetworkRepository {
    override suspend fun addBug(loginRequest: AddBugRequest): String {
        return retrofitAPIService.addBugUser(loginRequest.action,loginRequest.sheetName,loginRequest.image,loginRequest.description)
    }
}