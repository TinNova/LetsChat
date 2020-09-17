import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import tin.novakovic.data.MessageEntity
import tin.novakovic.data.MessageRepository
import tin.novakovic.domain.MessageConverter
import tin.novakovic.domain.MessageHelper
import tin.novakovic.domain.MessageModel

class MessageHelperUnitTest {

    @Mock
    lateinit var mockMessageRepository: MessageRepository

    @Mock
    lateinit var mockMessageConverter: MessageConverter


    private lateinit var target: MessageHelper

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = MessageHelper(mockMessageRepository, mockMessageConverter)
    }

    @Test
    fun test_insertMessage() {
        //given
        val message = "message"
        val isSent = true
        val completable = mock<Completable>()

        whenever(mockMessageRepository.insertMessage(message, isSent)).thenReturn(completable)

        //when
        val returnValue = target.insertMessage(message, isSent).test()

        //then
        assertEquals(returnValue, null)


    }

    @Test
    fun test_fetchLatestMessage() {
        //given
        val mockMessageEntity = mock<MessageEntity>()
        val mockMessages = mock<List<MessageModel>>()

        whenever(mockMessageRepository.fetchLatestMessage()).thenReturn(
            Single.just(
                mockMessageEntity
            )
        )
        whenever(mockMessageConverter.convertMessage(mockMessageEntity)).thenReturn(mockMessages)

        //when
        val returnValue = target.fetchLatestMessage().test()

        //then
        val result = returnValue.values().first()
        assertEquals(result, mockMessages)
    }

    @Test
    fun test_fetchAllMessages() {
        //given
        val mockMessageEntity = mock<List<MessageEntity>>()
        val mockMessages = listOf(MessageModel(), MessageModel(), MessageModel())

        whenever(mockMessageRepository.fetchAllMessages()).thenReturn(Single.just(mockMessageEntity))
        whenever(mockMessageConverter.convertMessages(mockMessageEntity)).thenReturn(mockMessages)

        //when
        val returnValue = target.fetchAllMessages().test()

        //then
        val result = returnValue.values().first()
        assertEquals(result, mockMessages)
    }
}
