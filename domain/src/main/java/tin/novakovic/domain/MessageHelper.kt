package tin.novakovic.domain

import io.reactivex.Completable
import io.reactivex.Single
import tin.novakovic.data.MessageEntity
import tin.novakovic.data.MessageRepository
import tin.novakovic.domain.MessageType.*
import java.text.SimpleDateFormat
import javax.inject.Inject

class MessageHelper @Inject constructor(private val messageRepo: MessageRepository) {

    fun insertMessage(message: String, isSent: Boolean): Completable =
        messageRepo.insertMessage(message, isSent)

    fun fetchLatestMessage(): Single<MessageModel> =
        messageRepo.fetchLatestMessage()
            .map {
                MessageModel(
                    message = it.message,
                    isSent = it.isSent,
                    messageType = TAIL_MESSAGE
                )
            }

    fun fetchAllMessages(): Single<List<MessageModel>> =
        messageRepo.fetchAllMessages()
            .map { mapToMessageModel(it) }
            .map { it.reversed() }


    // what if it's the first time and the list is empty?
    private fun mapToMessageModel(entityMessages: List<MessageEntity>): List<MessageModel> {

        var previousMessage = MessageEntity()
        var messageModels: MutableList<MessageModel> = mutableListOf()

        entityMessages.forEachIndexed { index, currentMessage ->

            // first in list
            if (index == 0) {

                // if hour elapsed show timeStamp + message
                if (oneHourElapsed(System.currentTimeMillis(), currentMessage.timestamp)) {

                    messageModels.add(
                        MessageModel(
                            dayOfMessage = convertTimeStampToDay(currentMessage.timestamp),
                            hourAndTimeOfMessage = convertTimeStampTo24Hr(currentMessage.timestamp),
                            messageType = TIME_STAMP
                        )
                    )

                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = TAIL_MESSAGE
                        )
                    )

                    // if hour not elapsed, only show message
                } else {
                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = TAIL_MESSAGE
                        )
                    )
                }
                // if not first in list
            } else {
                // if hour elapsed show timeStamp + message
                if (oneHourElapsed(previousMessage.timestamp, currentMessage.timestamp)) {

                    messageModels.add(
                        MessageModel(
                            dayOfMessage = convertTimeStampToDay(currentMessage.timestamp),
                            hourAndTimeOfMessage = convertTimeStampTo24Hr(currentMessage.timestamp),
                            messageType = TIME_STAMP
                        )
                    )

                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = TAIL_MESSAGE
                        )
                    )
                }
                // if current and previous message sent by same person
                else if (previousMessage.isSent && currentMessage.isSent ||
                    !previousMessage.isSent && !currentMessage.isSent
                ) {

                    // if 20 seconds elapsed show tail message
                    if (twentySecsElapsed(previousMessage.timestamp, currentMessage.timestamp)) {

                        messageModels.add(
                            MessageModel(
                                message = currentMessage.message,
                                isSent = currentMessage.isSent,
                                messageType = TAIL_MESSAGE
                            )
                        )

                        // if 20 seconds not elapsed show message
                    } else {
                        messageModels.add(
                            MessageModel(
                                message = currentMessage.message,
                                isSent = currentMessage.isSent,
                                messageType = MESSAGE
                            )
                        )
                    }

                    // if current message and previous message sent by different people show tail message
                } else {
                    messageModels.add(
                        MessageModel(
                            message = currentMessage.message,
                            isSent = currentMessage.isSent,
                            messageType = TAIL_MESSAGE
                        )
                    )
                }
            }

            previousMessage = currentMessage
        }

        return messageModels
    }

    private fun convertTimeStampToDay(currentTimeStampInSecs: Long): String {
        val pattern = "EEE"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(currentTimeStampInSecs)
    }

    private fun convertTimeStampTo24Hr(currentTimeStampInSecs: Long): String {
        val pattern = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(currentTimeStampInSecs)
    }

    private fun twentySecsElapsed(
        previousMessageTimeInMilliSecs: Long,
        currentMessageTimeStampInSecs: Long
    ): Boolean {

        val elapsedTime = previousMessageTimeInMilliSecs - currentMessageTimeStampInSecs

        return elapsedTime >= TWENTY_SECS_IN_MILLI_SECS
    }

    private fun oneHourElapsed(
        previousMessageOrCurrentTimeInMilliSecs: Long,
        currentMessageTimeStampInMilliSecs: Long
    ): Boolean {

        val elapsedTime =
            previousMessageOrCurrentTimeInMilliSecs - currentMessageTimeStampInMilliSecs

        return elapsedTime >= FIVE_MIN_IN_MILLI_SECS
    }

    companion object {
        const val ONE_HOUR_IN_MILLI_SECS = 3600000L
        const val FIVE_MIN_IN_MILLI_SECS = 300000L
        const val TWENTY_SECS_IN_MILLI_SECS = 20000L
    }
}