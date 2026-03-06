package com.example.eventreporter.queue

import com.example.eventreporter.event.Event
import com.example.eventreporter.sender.EventSender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//负责异步处理事件的排队和发送
class EventQueue(
    private val senders: List<EventSender>,
    private val scope: CoroutineScope
) {
    private val channel = Channel<Event>(Channel.UNLIMITED)

    init {
        startConsumer()
    }

    //从 Channel 中接收事件并分发给所有注册的发送器进行处理
    private fun startConsumer() {
        scope.launch {
            channel.receiveAsFlow().collect { event ->
                senders.forEach { sender ->
                    try {
                        withContext(Dispatchers.IO) {
                            sender.send(event)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    //非阻塞操作，事件会被放入 Channel 中等待消费者处理
    fun enqueue(event: Event) {
        scope.launch {
            channel.send(event)
        }
    }

}