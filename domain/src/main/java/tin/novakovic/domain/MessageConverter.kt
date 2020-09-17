package tin.novakovic.domain

import tin.novakovic.data.MessageEntity
import javax.inject.Inject

class MessageConverter @Inject constructor(private val dateHelper: DateHelper) {

    lateinit var mostRecentMessage: MessageEntity

    // what about when the chat is empty?
    fun convertMessage(currentMessage: MessageEntity): List<MessageModel> {

        val messageModels: MutableList<MessageModel> = mutableListOf()

        if (mostRecentMessage.timestamp == 0L) {

            messageModels.add(
                MessageModel(
                    message = currentMessage.message,
                    isSent = currentMessage.isSent,
                    messageType = MessageType.TAIL_MESSAGE
                )
            )

        } else {

            // if currentMessage and mostRecent were sent by the same person
            if (currentMessage.isSent && mostRecentMessage.isSent ||
                !currentMessage.isSent && !mostRecentMessage.isSent
            ) {

                // if twenty seconds elapsed show new message with tail
                if (dateHelper.twentySecsElapsed(currentMessage.timestamp, mostRecentMessage.timestamp)) {

                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = MessageType.TAIL_MESSAGE
                        )
                    )
                    // if twenty seconds has not elapsed update previous message and show new message with tail
                } else {
                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = MessageType.TAIL_MESSAGE
                        )
                    )

                    // remove tail from mostRecent message and give it to currentMessage
                    messageModels.add(
                        MessageModel(
                            message = mostRecentMessage.message,
                            isSent = mostRecentMessage.isSent,
                            messageType = MessageType.MESSAGE
                        )
                    )
                }

                // currentMessage and mostRecent were sent by different people
            } else {

                messageModels.add(
                    MessageModel(
                        message = currentMessage.message,
                        isSent = currentMessage.isSent,
                        messageType = MessageType.TAIL_MESSAGE
                    )
                )
            }
        }

        mostRecentMessage = currentMessage
        return messageModels
    }

    // what if it's the first time and the list is empty?
    fun convertMessages(entityMessages: List<MessageEntity>): List<MessageModel> {

        var previousMessage = MessageEntity()
        val messageModels: MutableList<MessageModel> = mutableListOf()

        entityMessages.forEachIndexed { index, currentMessage ->

            // first in list
            if (index == 0) {

                // if hour elapsed show timeStamp + message
                if (dateHelper.oneHourElapsed(System.currentTimeMillis(), currentMessage.timestamp)) {

                    messageModels.add(
                        MessageModel(
                            dayOfMessage = dateHelper.convertTimeStampToDay(currentMessage.timestamp),
                            hourAndTimeOfMessage = dateHelper.convertTimeStampTo24Hr(currentMessage.timestamp),
                            messageType = MessageType.TIME_STAMP
                        )
                    )

                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = MessageType.TAIL_MESSAGE
                        )
                    )

                    // if hour not elapsed, only show message
                } else {
                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = MessageType.TAIL_MESSAGE
                        )
                    )
                }
                // if not first in list
            } else {
                // if hour elapsed show timeStamp + message
                if (dateHelper.oneHourElapsed(previousMessage.timestamp, currentMessage.timestamp)) {

                    messageModels.add(
                        MessageModel(
                            dayOfMessage = dateHelper.convertTimeStampToDay(currentMessage.timestamp),
                            hourAndTimeOfMessage = dateHelper.convertTimeStampTo24Hr(currentMessage.timestamp),
                            messageType = MessageType.TIME_STAMP
                        )
                    )

                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = MessageType.TAIL_MESSAGE
                        )
                    )
                }
                // if current and previous message sent by same person
                else if (previousMessage.isSent && currentMessage.isSent ||
                    !previousMessage.isSent && !currentMessage.isSent
                ) {

                    // if 20 seconds elapsed show tail message
                    if (dateHelper.twentySecsElapsed(previousMessage.timestamp, currentMessage.timestamp)) {

                        messageModels.add(
                            MessageModel(
                                message = currentMessage.message,
                                isSent = currentMessage.isSent,
                                messageType = MessageType.TAIL_MESSAGE
                            )
                        )

                        // if 20 seconds not elapsed show message
                    } else {
                        messageModels.add(
                            MessageModel(
                                message = currentMessage.message,
                                isSent = currentMessage.isSent,
                                messageType = MessageType.MESSAGE
                            )
                        )
                    }

                    // if current message and previous message sent by different people show tail message
                } else {
                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = MessageType.TAIL_MESSAGE
                        )
                    )
                }
            }

            previousMessage = currentMessage
        }

        return messageModels
    }
}
