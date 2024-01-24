package com.app.bugtrackerapp.data.remote

import com.app.bugtrackerapp.data.remote.network.RetrofitAPIService
import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import com.app.bugtrackerapp.model.Bug
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val retrofitAPIService: RetrofitAPIService)  :
    NetworkRepository {
    override suspend fun loginUser(loginRequest: AddBugRequest): Bug {
        return retrofitAPIService.addBugUser(loginRequest)
    }
}