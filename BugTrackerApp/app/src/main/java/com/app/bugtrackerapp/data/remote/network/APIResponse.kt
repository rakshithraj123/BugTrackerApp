package com.app.bugtrackerapp.data.remote.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable



class APIResponse<T> : Serializable  {

    @SerializedName("data")
    private var data: T? = null

    fun getData(): T? {
        return data
    }

    fun setData(data: T?) {
        this.data = data
    }
}