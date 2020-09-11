package tin.novakovic.data

import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageDao: MessageDao) {

    fun insertMessage(message: String, isSent: Boolean) : Completable =
        messageDao.insertMessage(MessageEntity(message, isSent, System.currentTimeMillis() / 1000L))

    fun fetchLatestMessage(): Single<MessageEntity> =
        messageDao.fetchLatestMessage()

    fun fetchAllMessages(): Single<List<MessageEntity>> =
        messageDao.fetchAllMessages()
}