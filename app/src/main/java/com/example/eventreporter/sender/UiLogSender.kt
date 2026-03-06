package com.example.eventreporter.sender

import com.example.eventreporter.event.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//UI同步展示日志
class UiLogSender : EventSender {
    //用可变共享流来存放并观察日志
    private val _logFlow = MutableSharedFlow<String>(extraBufferCapacity = 64)
    val logFlow = _logFlow.asSharedFlow()

    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    override suspend fun send(event: Event) {
        val time = dateFormat.format(event.timestamp)
        val logEntry = "[$time] 上报事件: ${event.name}, 参数: ${event.params}"
        _logFlow.emit(logEntry)
    }
}
