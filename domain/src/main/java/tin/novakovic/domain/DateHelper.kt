package tin.novakovic.domain

import java.text.SimpleDateFormat
import javax.inject.Inject

class DateHelper @Inject constructor() {

    fun convertTimeStampToDay(currentTimeStampInMilliSecs: Long): String {
        val pattern = "EEE"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(currentTimeStampInMilliSecs)
    }

    fun convertTimeStampTo24Hr(currentTimeStampInSecs: Long): String {
        val pattern = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(currentTimeStampInSecs)
    }

    fun twentySecsElapsed(
        moreRecentTimeInMilliSecs: Long,
        lessRecentTimeStampInMilliSecs: Long
    ): Boolean {

        val elapsedTime = moreRecentTimeInMilliSecs - lessRecentTimeStampInMilliSecs

        return elapsedTime >= TWENTY_SECS_IN_MILLI_SECS
    }

    fun oneHourElapsed(
        moreRecentTimeInMilliSecs: Long,
        lessRecentTimeStampInMilliSecs: Long
    ): Boolean {

        val elapsedTime =
            moreRecentTimeInMilliSecs - lessRecentTimeStampInMilliSecs

        return elapsedTime >= ONE_HOUR_IN_MILLI_SECS
    }

    companion object {
        const val ONE_HOUR_IN_MILLI_SECS = 3600000L
        const val TWENTY_SECS_IN_MILLI_SECS = 20000L
    }
}