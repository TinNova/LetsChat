package tin.novakovic.data

import androidx.room.TypeConverter
import java.sql.Date

class DatabaseConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time.toLong()
    }
}