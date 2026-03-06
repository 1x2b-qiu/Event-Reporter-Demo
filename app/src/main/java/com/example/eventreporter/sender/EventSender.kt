package com.example.eventreporter.sender

import com.example.eventreporter.event.Event

interface EventSender {
    suspend fun send(event: Event)
}