package com.app.bugtrackerapp.data

import android.util.Log
import com.app.bugtrackerapp.data.remote.NetworkRepository
import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import com.app.bugtrackerapp.model.Bug

import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
) : Repository {


    override suspend fun loginUser(loginRequest: AddBugRequest): Bug {
        Log.d("loginUser","RepositoryImpl");
        return networkRepository.loginUser(loginRequest)


    }


}