package tin.novakovic.letschat

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tin.novakovic.data.DatabaseConverter
import tin.novakovic.data.MessageDao
import tin.novakovic.data.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMessageDao(): MessageDao

    companion object {
        const val DATABASE_NAME: String = "app_db"
    }
}