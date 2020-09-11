package tin.novakovic.letschat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.received_chat_item.view.*
import kotlinx.android.synthetic.main.sent_chat_item.view.*
import tin.novakovic.data.MessageEntity
import tin.novakovic.letschat.R

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2
    }

    fun addMessages(messages: List<MessageEntity>) {
        this.messages = messages as MutableList<MessageEntity>
        notifyDataSetChanged()
    }

    fun addLatestMessage(message: MessageEntity) {
        messages.add(message)
        notifyItemInserted(messages.size)
    }

    // TODO: needs to be changed to a Domain Model with a formatted string as the date
    private var messages: MutableList<MessageEntity> = mutableListOf()

    override fun getItemViewType(position: Int): Int =
        if (messages[position].isSent) TYPE_SENT else TYPE_RECEIVED


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SENT) {
            SentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sent_chat_item, parent, false)
            )
        } else {
            ReceivedViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.received_chat_item, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_SENT -> (holder as SentViewHolder).bind(messages[position])
            TYPE_RECEIVED -> (holder as ReceivedViewHolder).bind(messages[position])
        }
    }


    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: MessageEntity) {
            itemView.sent_message.text = message.message
        }
    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: MessageEntity) {
            itemView.received_message.text = message.message
        }
    }

}
