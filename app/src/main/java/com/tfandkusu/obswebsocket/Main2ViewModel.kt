package com.tfandkusu.obswebsocket

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfandkusu.obswebsocket.data.OBSRequest
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient

class Main2ViewModel : ViewModel() {
    private val client = OkHttpClient.Builder()
        .build()

    private val scarletInstance = Scarlet.Builder()
        .webSocketFactory(client.newWebSocketFactory("ws://192.168.11.2:4444/"))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .build()

    private val service = scarletInstance.create<OBSService>()

    private var messageId = 1

    private var disposable: Disposable? = null

    companion object {
        private const val TAG = "OBS"
    }

    fun onCreate() {
        disposable = service.observeMessageReceived().subscribe {
            Log.d(TAG, it.toString())
        }
    }

    fun saveReplayBuffer() {
        service.send(OBSRequest("SaveReplayBuffer", messageId.toString()))
        messageId += 1
    }

    override fun onCleared() {
        disposable?.dispose()
        client.dispatcher.executorService.shutdown()
    }

}
