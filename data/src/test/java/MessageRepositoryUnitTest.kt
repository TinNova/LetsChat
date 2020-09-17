import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import tin.novakovic.data.MessageDao
import tin.novakovic.data.MessageEntity
import tin.novakovic.data.MessageRepository

class MessageRepositoryUnitTest {

    @Mock
    lateinit var mockMessageDao: MessageDao

    private lateinit var target: MessageRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = MessageRepository(mockMessageDao)
    }

    @Test
    fun test_insertMessage_returnCompletable() {
        //given
        val message = "message"
        val isSent = true
        val completable = mock<Completable>()

        whenever(mockMessageDao.insertMessage(MessageEntity(message, isSent, 0L))).thenReturn(
            completable
        )

        //when
        target.insertMessage(message, isSent)

        //then

    }

    @Test
    fun test_fetchLatestMessage_returnMessageEntity() {

        //given
        val mockResponse = mock<MessageEntity>()
        whenever(mockMessageDao.fetchLatestMessage()).thenReturn(Single.just(mockResponse))

        //when
        val returnValue = target.fetchLatestMessage().test()

        //then
        val result = returnValue.values().first()
        assertEquals(result, mockResponse)
    }

    @Test
    fun test_fetchAllMessages_returnListOfMessageEntity() {
        //given
        val mockResponse = mock<List<MessageEntity>>()
        whenever(mockMessageDao.fetchAllMessages()).thenReturn(Single.just(mockResponse))

        //when
        val returnValue = target.fetchAllMessages().test()

        //then
        val result = returnValue.values().first()
        assertEquals(result, mockResponse)
    }

}