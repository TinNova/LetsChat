package tin.novakovic.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MessageDao {

    /**
     * OnConflictStrategy.ABORT
     *
     * ABORT is used because this shouldn't ever happen as timestamp will always be unique
     * but just in case if there is a duplication we will abort saving the new message */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertMessage(messageEntity: MessageEntity): Completable

    @Query("SELECT * FROM messageEntity ORDER BY timestamp DESC LIMIT 1")
    fun fetchLatestMessage(): Single<MessageEntity>

    @Query("SELECT * FROM messageEntity ORDER BY timestamp desc")
    fun fetchAllMessages(): Single<List<MessageEntity>>

}