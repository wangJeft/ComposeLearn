package com.jeft.chatty.screens.conversation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf

class ConversationUiState(
    val conversationUserId: String,
    initialMessage: List<Message>
) {
    private val _message: MutableList<Message> = mutableStateListOf(*initialMessage.toTypedArray())
    val messages: List<Message> = _message

    fun addMessage(msg: Message) {
        _message.add(0, msg)
    }
}

@Immutable
data class Message(
    val isUserMe: Boolean,
    val content: String,
    val timestamp: String,
    val image: Int? = null
)