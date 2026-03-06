package com.example.eventreporter.sender

import android.util.Log
import com.example.eventreporter.event.Event

//把事件发送到控制台
class ConsoleSender(
    private val tag: String = "EventReporter"
) : EventSender {
    override suspend fun send(event: Event) {
        Log.d(tag, "ConsoleSender -> $event")
    }
}