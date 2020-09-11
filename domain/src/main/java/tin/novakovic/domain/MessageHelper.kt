package tin.novakovic.domain

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tin.novakovic.data.MessageEntity
import tin.novakovic.data.MessageRepository
import javax.inject.Inject

class MessageHelper @Inject constructor(private val messageRepo: MessageRepository) {

    fun insertMessage(message: String, isSent: Boolean): Completable =
        messageRepo.insertMessage(message, isSent)

    fun fetchLatestMessage(): Single<MessageEntity> =
        messageRepo.fetchLatestMessage()

    fun fetchAllMessages(): Single<List<MessageEntity>> =
        messageRepo.fetchAllMessages()
}