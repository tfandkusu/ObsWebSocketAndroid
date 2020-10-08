package com.tfandkusu.obswebsocket

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString


class MainViewModel : ViewModel() {
    private val gson = Gson()

    private var messageId = 1
    private val client = OkHttpClient.Builder()
        .build()

    private var webSocket: WebSocket? = null

    companion object {
        private const val TAG = "OBS"
    }

    fun onCreate() = GlobalScope.launch(Dispatchers.IO) {
        val request = Request.Builder()
            .url("ws://192.168.11.2:4444/")
            .build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "onClosed")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "onClosing")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.d(TAG, "onFailure")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "onMessage $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d(TAG, "onMessage %d bytes".format(bytes.size))
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "onOpen")
            }
        })
        Log.d(TAG, "onCreate end")
    }

    fun saveReplayBuffer() = GlobalScope.launch(Dispatchers.IO) {
        val map = mapOf("request-type" to "SaveReplayBuffer", "message-id" to messageId.toString())
        webSocket?.send(gson.toJson(map))
        messageId += 1
    }

    override fun onCleared() {
        client.dispatcher.executorService.shutdown()
        Log.d(TAG, "shutdown")
    }
}
