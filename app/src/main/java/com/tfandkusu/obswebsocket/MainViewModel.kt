package com.tfandkusu.obswebsocket

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString


class MainViewModel : ViewModel() {
    private val client = OkHttpClient.Builder()
        .build()

    companion object {
        private const val TAG = "OBS"
    }

    fun onCreate() = GlobalScope.launch(Dispatchers.IO) {
        val request = Request.Builder()
            .url("ws://192.168.11.2:4444/")
            .build()
        client.newWebSocket(request, object: WebSocketListener() {
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
        Log.d(TAG,"onCreate end")
    }

    override fun onCleared() {
        client.dispatcher.executorService.shutdown()
        Log.d(TAG,"shutdown")
    }
}
