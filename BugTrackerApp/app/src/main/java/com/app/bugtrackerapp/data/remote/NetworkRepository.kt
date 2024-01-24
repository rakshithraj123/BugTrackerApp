package com.app.bugtrackerapp.data.remote

import android.content.Context
import android.net.Uri
import com.app.bugtrackerapp.data.remote.request.AddBugRequest


interface NetworkRepository {
    suspend fun addBug(loginRequest: AddBugRequest): String

    abstract fun uploadImage(baseContext: Context, imageUri: Uri?): String?

}