package com.byjus.headlines.headlines.models.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeadLineRequest(
    @SerializedName("country") val country: String,
    @SerializedName("apiKey") val apiKey: String
) : Serializable

