package com.app.bugtrackerapp.data.remote

import android.content.Context
import android.net.Uri
import com.app.bugtrackerapp.data.remote.network.RetrofitAPIService
import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val retrofitAPIService: RetrofitAPIService)  :
    NetworkRepository {
    override suspend fun addBug(loginRequest: AddBugRequest): String {
        return retrofitAPIService.addBugUser(loginRequest.action,loginRequest.sheetName,loginRequest.image,loginRequest.description)
    }

    override  fun uploadImage(baseContext: Context, fileUri: Uri?): String {
        if (fileUri != null) {
            val ref: StorageReference = FirebaseStorage.getInstance().reference
                .child(UUID.randomUUID().toString())

            var result = Tasks.await(ref.putFile(fileUri));

            if (result.task.isSuccessful) {
                var resultUri = Tasks.await(ref.downloadUrl);
                return resultUri.toString()
            } else {
                return ""
            }
        }
        return ""
    }
}