package tin.novakovic.letschat.ui

import android.view.MenuItem
import androidx.annotation.StringRes
import tin.novakovic.data.MessageEntity
import tin.novakovic.domain.MessageModel

sealed class MainViewState {

    data class ShowLatestMessage(val messageEntity: MessageModel) : MainViewState()

    data class ShowAllMessages(val messages: List<MessageModel>) : MainViewState()

    data class SwitchSender(val item: MenuItem, @StringRes val menuItemTitle: Int) : MainViewState()

    object ShowError : MainViewState()
}