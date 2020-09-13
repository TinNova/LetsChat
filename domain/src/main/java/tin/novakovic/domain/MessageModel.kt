package tin.novakovic.domain

import tin.novakovic.domain.MessageType.*

data class MessageModel(
    val message: String = "",
    val isSent: Boolean = false,
    val dayOfMessage: String = "",
    val hourAndTimeOfMessage: String = "",
    val messageType: MessageType = MESSAGE
)

enum class MessageType {
    TAIL_MESSAGE,
    MESSAGE,
    TIME_STAMP
}