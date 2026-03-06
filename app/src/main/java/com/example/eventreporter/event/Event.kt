package com.example.eventreporter.event

//定义事件的数据结构
data class Event(
    val name: String,
    val params: Map<String, Any?> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
)