import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import tin.novakovic.domain.DateHelper

class DateHelperUnitTest {

    private lateinit var target: DateHelper

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = DateHelper()
    }

    @Test
    fun test_convertTimeStampToDay() {
        //given
        val currentTimeStampInSecs = 1600284506598L

        //when
        val result = target.convertTimeStampToDay(currentTimeStampInSecs)

        //then
        assertEquals(result, "Wed")
    }

    @Test
    fun test_convertTimeStampTo24Hr() {
        //given
        val currentTimeStampInSecs = 1600284506598L

        //when
        val result = target.convertTimeStampTo24Hr(currentTimeStampInSecs)

        //then
        assertEquals(result, "20:28")
    }

    @Test
    fun test_twentySecsElapsed_returnFalse() {
        //given
        val moreRecentTime = 1600284506598L
        val lessRecentTime = 1600284506500L

        //when
        val result = target.twentySecsElapsed(moreRecentTime,lessRecentTime)

        //then
        assertEquals(result, false)
    }

    @Test
    fun test_twentySecsElapsed_returnTrue() {
        //given
        val moreRecentTime = 1600284506598L
        val lessRecentTime = 1600284000000L

        //when
        val result = target.twentySecsElapsed(moreRecentTime,lessRecentTime)

        //then
        assertEquals(result, true)
    }

    @Test
    fun test_oneHourElapsed_returnFalse() {
        //given
        val moreRecentTime = 1600284506598L
        val lessRecentTime = 1600284000000L

        //when
        val result = target.oneHourElapsed(moreRecentTime, lessRecentTime)

        //then
        assertEquals(result, false)
    }

    @Test
    fun test_oneHourElapsed_returnTrue() {
        //given
        val moreRecentTime = 1600284506598L
        val lessRecentTime = 1500000000000L

        //when
        val result = target.oneHourElapsed(moreRecentTime, lessRecentTime)

        //then
        assertEquals(result, true)
    }

}
