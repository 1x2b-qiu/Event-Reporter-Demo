package com.example.eventreporter.sender

import android.util.Log
import com.example.eventreporter.event.Event
import kotlinx.coroutines.delay

//网络发送
class NetworkSender(
    private val tag: String = "EventReporter"
) : EventSender {
    override suspend fun send(event: Event) {
        //模拟网络耗时
        delay(300)
        Log.d(tag, "NetworkSender -> $event")
    }
}