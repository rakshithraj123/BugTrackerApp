package com.app.bugtrackerapp.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.app.bugtrackerapp.data.remote.NetworkRepository
import com.app.bugtrackerapp.data.remote.request.AddBugRequest

import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
) : Repository {


    override suspend fun addBug(loginRequest: AddBugRequest): String {
        Log.d("loginUser","RepositoryImpl");
        return networkRepository.addBug(loginRequest)


    }

    override fun uploadImage(baseContext: Context, imageUri: Uri?): String? {
        return networkRepository.uploadImage(baseContext, imageUri)
    }


}