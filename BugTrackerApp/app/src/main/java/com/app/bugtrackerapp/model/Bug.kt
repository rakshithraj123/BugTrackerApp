package com.app.bugtrackerapp.model

import com.google.gson.annotations.SerializedName

data class Bug(
    @SerializedName("name") var imageUrl: String? = null,
    @SerializedName("email") var description: String? = null
)

