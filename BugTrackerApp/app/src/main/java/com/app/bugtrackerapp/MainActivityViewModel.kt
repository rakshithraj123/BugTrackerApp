package com.app.bugtrackerapp

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bugtrackerapp.data.Repository
import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var description: String = "";
    var imageUri: Uri? = null;
    val _sharedImage = MutableLiveData<Uri>()
    val loading = MutableLiveData<Boolean>()
    /**
     * add bug to google sheet
     */
    suspend fun addBug(baseContext: Context) {
        loading.postValue(true)
        var downloadImageUrl = repository.uploadImage(baseContext, imageUri)
        var sheetName = getSheetName()
        repository.addBug(AddBugRequest("insert", sheetName, downloadImageUrl, description))
        loading.postValue(false)
    }

    /**
     * upload image to firebase
     */


    private fun getSheetName(): String {
        var format = SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z")
        val newDate: Date = Date()
        format = SimpleDateFormat("MM-DD-YYYY")
        return format.format(newDate)
    }
}