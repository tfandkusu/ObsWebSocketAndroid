package com.tfandkusu.obswebsocket.data

import com.google.gson.annotations.SerializedName

data class OBSRequest(
    @SerializedName("request-type") val requestType: String,
    @SerializedName("message-id") val messageId: String
)
