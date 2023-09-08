package com.jeft.chatty.screens.conversation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeft.chatty.screens.conversation.mock.initialMessages

@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    uiState: ConversationUiState = ConversationUiState(
        initialMessage = initialMessages,
        conversationUserId = "1024"
    )
) {
    // TODO:  

}