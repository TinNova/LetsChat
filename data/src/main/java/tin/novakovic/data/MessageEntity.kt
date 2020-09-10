package tin.novakovic.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class MessageEntity(
    val message: String,
    val isSent: Boolean,
    @PrimaryKey val timestamp: Date
)