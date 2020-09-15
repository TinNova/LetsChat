package tin.novakovic.letschat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.received_chat_item.view.*
import kotlinx.android.synthetic.main.sent_chat_item.view.*
import kotlinx.android.synthetic.main.timestamp_chat_item.view.*
import tin.novakovic.domain.MessageModel
import tin.novakovic.domain.MessageType
import tin.novakovic.letschat.R


open class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2
        private const val TYPE_TIMESTAMP = 3
    }

    fun addMessages(messages: List<MessageModel>) {
        this.messages = messages.toMutableList()
        notifyDataSetChanged()
    }

    fun addLatestMessage(message: MessageModel) {
        messages.add(message)
        notifyItemInserted(messages.size)
    }

    fun updatePreviousMessage(message: MessageModel) {
        messages.removeAt(messages.size - 1)
        notifyItemChanged(messages.size, Unit)
        messages.add(message)
        notifyItemChanged(messages.size, Unit)
    }

    fun addTimeStamp(message: MessageModel) {
        messages.add(message)
        notifyItemInserted(messages.size)
    }

    // TODO: needs to be changed to a Domain Model with a formatted string as the date
    private var messages: MutableList<MessageModel> = mutableListOf()
    fun getLastIndexOfMessages(): Int = messages.size - 1

    override fun getItemViewType(position: Int): Int {

        return when {
            messages[position].messageType == MessageType.TIME_STAMP -> {
                TYPE_TIMESTAMP
            }
            messages[position].isSent -> TYPE_SENT
            else -> TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SENT -> SentViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.sent_chat_item, parent, false)
            )
            TYPE_RECEIVED -> ReceivedViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.received_chat_item, parent, false)
            )
            else -> TimeStampViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.timestamp_chat_item, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_SENT -> (holder as SentViewHolder).bind(messages[position])
            TYPE_RECEIVED -> (holder as ReceivedViewHolder).bind(messages[position])
            TYPE_TIMESTAMP -> (holder as TimeStampViewHolder).bind(messages[position])
        }
    }


    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: MessageModel) {
            itemView.sent_message.text = message.message

            when (message.messageType) {
                MessageType.TAIL_MESSAGE -> itemView.sent_message.setBackgroundResource(R.drawable.bubble_sender_tail)
                MessageType.MESSAGE -> itemView.sent_message.setBackgroundResource(R.drawable.bubble_sender)
            }
        }
    }

    inner class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: MessageModel) {
            itemView.received_message.text = message.message

            when (message.messageType) {
                MessageType.TAIL_MESSAGE -> itemView.received_message.setBackgroundResource(R.drawable.bubble_receiver_tail)
                MessageType.MESSAGE -> itemView.received_message.setBackgroundResource(R.drawable.bubble_receiver)
            }
        }
    }

    inner class TimeStampViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: MessageModel) {
            itemView.timestamp_date.text = message.dayOfMessage
            itemView.timestamp_time.text = message.hourAndTimeOfMessage
        }
    }

}
