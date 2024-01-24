package com.app.bugtrackerapp.data.remote

import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import com.app.bugtrackerapp.model.Bug


interface NetworkRepository {
    suspend fun loginUser(loginRequest: AddBugRequest): Bug
}