package com.example.linisnasugbu.service

data class Message(
    val id: String,
    val title: String,
    val body: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

class LinisMessagingService {
    private val messages = mutableListOf<Message>()

    fun sendNotification(title: String, body: String) {
        val newMessage = Message(
            id = System.currentTimeMillis().toString(),
            title = title,
            body = body
        )
        messages.add(newMessage)
    }

    fun getAllMessages(): List<Message> = messages.toList()

    fun markAsRead(messageId: String) {
        val index = messages.indexOfFirst { it.id == messageId }
        if (index != -1) {
            messages[index] = messages[index].copy(isRead = true)
        }
    }

    fun clearAll() {
        messages.clear()
    }
}