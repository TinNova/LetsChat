package tin.novakovic.domain

import io.reactivex.Completable
import io.reactivex.Single
import tin.novakovic.data.MessageEntity
import tin.novakovic.data.MessageRepository
import tin.novakovic.domain.MessageType.*
import java.text.SimpleDateFormat
import javax.inject.Inject

class MessageHelper @Inject constructor(
    private val messageRepo: MessageRepository,
    private val messageConverter: MessageConverter
) {


    fun insertMessage(message: String, isSent: Boolean): Completable =
        messageRepo.insertMessage(message, isSent)

    fun fetchLatestMessage(): Single<List<MessageModel>> =
        messageRepo.fetchLatestMessage()
            .map {
                messageConverter.convertMessage(it)
            }

    fun fetchAllMessages(): Single<List<MessageModel>> =
        messageRepo.fetchAllMessages()
            .map {
                messageConverter.mostRecentMessage = if (it.isNotEmpty()) it[0] else MessageEntity()
                messageConverter.convertMessages(it)
            }
            .map { it.reversed() }
}