package tin.novakovic.letschat.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import tin.novakovic.data.MessageDao
import tin.novakovic.letschat.AppDatabase
import tin.novakovic.letschat.AppDatabase.Companion.DATABASE_NAME
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): MessageDao {
        return db.getMessageDao()
    }
}