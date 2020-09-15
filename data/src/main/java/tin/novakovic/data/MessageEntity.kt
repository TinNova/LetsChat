package tin.novakovic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    val message: String = "",
    val isSent: Boolean = false,
    @PrimaryKey val timestamp: Long = 0L
)