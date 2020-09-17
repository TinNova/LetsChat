import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import tin.novakovic.data.MessageEntity
import tin.novakovic.domain.DateHelper
import tin.novakovic.domain.MessageConverter
import tin.novakovic.domain.MessageModel
import tin.novakovic.domain.MessageType

class MessageConverterUnitTest {

    @Mock
    lateinit var mockDateHelper: DateHelper

    private lateinit var target: MessageConverter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = MessageConverter(mockDateHelper)
    }

    private val message1 = "message1"
    private val message2 = "message2"
    private val message3 = "message3"
    private val message4 = "message4"
    private val message5 = "message5"

    private val messagesEntity = listOf(
        MessageEntity(
            message = message4,
            isSent = false,
            timestamp = 0L //Wednesday, 16 September 2020 18:10:05
        ),
        MessageEntity(
            message = message3,
            isSent = true,
            timestamp = 0L //Wednesday, 16 September 2020 18:10:00
        ),
        MessageEntity(
            message = message2,
            isSent = false,
            timestamp = 0L //Wednesday, 16 September 2020 18:00:05
        ),
        MessageEntity(
            message = message1,
            isSent = false,
            timestamp = 0L //Wednesday, 16 September 2020 18:00:00
        )
    )

    @Test
    fun test_convertMessages_testThatTailShowsCorrectlyWhenChangingSender() {
        //given
        val messages = listOf(
            MessageModel(
                message = message4,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                message = message3,
                isSent = true,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                message = message2,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                message = message1,
                isSent = false,
                messageType = MessageType.MESSAGE
            )
        )

        //when
        val result = target.convertMessages(messagesEntity)

        //then
        assertEquals(result, messages)
    }

    @Test
    fun test_convertMessages_oneHourElapsed() {
        //given
        val messages = listOf(
            MessageModel(
                message = message4,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            ), MessageModel(
                isSent = false,
                dayOfMessage = "Thu",
                hourAndTimeOfMessage = "18:10",
                messageType = MessageType.TIME_STAMP
            ),
            MessageModel(
                message = message3,
                isSent = true,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                isSent = false,
                dayOfMessage = "Thu",
                hourAndTimeOfMessage = "18:10",
                messageType = MessageType.TIME_STAMP
            ),
            MessageModel(
                message = message2,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                isSent = false,
                dayOfMessage = "Thu",
                hourAndTimeOfMessage = "18:10",
                messageType = MessageType.TIME_STAMP
            ),
            MessageModel(
                message = message1,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            )
        )

        whenever(
            mockDateHelper.oneHourElapsed(
                messagesEntity[0].timestamp,
                messagesEntity[1].timestamp
            )
        ).thenReturn(true)

        whenever(mockDateHelper.convertTimeStampToDay(messagesEntity[0].timestamp)).thenReturn("Thu")
        whenever(mockDateHelper.convertTimeStampTo24Hr(messagesEntity[0].timestamp)).thenReturn(
            "18:10"
        )

        //when
        val result = target.convertMessages(messagesEntity)

        //then
        assertEquals(result, messages)
    }

    @Test
    fun test_convertMessages_twentySecondsElapsed() {
        //given
        val messages = listOf(
            MessageModel(
                message = message4,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                message = message3,
                isSent = true,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                message = message2,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            ),
            MessageModel(
                message = message1,
                isSent = false,
                messageType = MessageType.TAIL_MESSAGE
            )
        )

        whenever(
            mockDateHelper.twentySecsElapsed(
                messagesEntity[0].timestamp,
                messagesEntity[1].timestamp
            )
        ).thenReturn(true)

        //when
        val result = target.convertMessages(messagesEntity)

        //then
        assertEquals(result, messages)

    }
}