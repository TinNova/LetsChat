package tin.novakovic.letschat.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import tin.novakovic.data.MessageDao
import tin.novakovic.letschat.AppDatabase
import tin.novakovic.letschat.AppDatabase.Companion.DATABASE_NAME
import javax.inject.Singleton

@Module
class RoomModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideAppDb(): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Reusable
    fun provideMessageDao(db: AppDatabase): MessageDao {
        return db.getMessageDao()
    }
}
