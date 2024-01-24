package com.app.bugtrackerapp.data.remote

import com.app.bugtrackerapp.data.remote.request.AddBugRequest


interface NetworkRepository {
    suspend fun addBug(loginRequest: AddBugRequest): String
}