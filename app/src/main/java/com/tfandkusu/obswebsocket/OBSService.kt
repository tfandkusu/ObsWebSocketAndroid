package com.tfandkusu.obswebsocket

import com.tfandkusu.obswebsocket.data.OBSMessageReceived
import com.tfandkusu.obswebsocket.data.OBSRequest
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface OBSService {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun send(request: OBSRequest)

    @Receive
    fun observeMessageReceived(): Flowable<OBSMessageReceived>
}
