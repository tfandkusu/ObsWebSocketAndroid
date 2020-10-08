package com.tfandkusu.obswebsocket.data

import com.google.gson.annotations.SerializedName

data class OBSMessageReceived(
    @SerializedName("message-id")
    private val messageId: String,
    private val status: String
)
