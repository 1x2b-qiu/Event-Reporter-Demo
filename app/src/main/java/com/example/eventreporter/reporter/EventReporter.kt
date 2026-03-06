package com.example.eventreporter.reporter

import com.example.eventreporter.event.Event
import com.example.eventreporter.queue.EventQueue
import com.example.eventreporter.sender.ConsoleSender
import com.example.eventreporter.sender.EventSender
import com.example.eventreporter.sender.NetworkSender
import kotlinx.coroutines.CoroutineScope

//事件上报
object EventReporter {
    private lateinit var queue: EventQueue

    //可插拔、可扩展
    fun init(
        scope: CoroutineScope,
        senders: List<EventSender> = listOf(ConsoleSender(), NetworkSender())
    ) {
        queue = EventQueue(senders, scope)
    }

    fun report(event: Event) {
        queue.enqueue(event)
    }

    //便于事件上报
    fun report(name: String, params: Map<String, Any?> = emptyMap()) {
        report(Event(name, params))
    }

}
