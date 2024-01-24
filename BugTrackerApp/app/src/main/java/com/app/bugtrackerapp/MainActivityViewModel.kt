package com.app.bugtrackerapp

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bugtrackerapp.data.Repository
import com.app.bugtrackerapp.data.remote.request.AddBugRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var description : String ="";
    var imageUri : Uri? = null;

    suspend fun addBug() {
        var sheetName = getSheetName()
        repository.addBug( AddBugRequest("insert",sheetName,imageUri?.toString(),description))
    }

    private fun getSheetName(): String {
        var format = SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z")
        val newDate: Date = Date()
        format = SimpleDateFormat("MM-DD-YYYY")
        return format.format(newDate)
    }

    val _sharedImage = MutableLiveData<Uri>()

}