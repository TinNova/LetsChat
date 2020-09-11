package tin.novakovic.letschat.ui

import android.view.MenuItem
import androidx.annotation.StringRes
import tin.novakovic.data.MessageEntity

sealed class MainViewState {

    data class ShowLatestMessage(val messageEntity: MessageEntity) : MainViewState()

    data class ShowAllMessages(val messages: List<MessageEntity>) : MainViewState()

    data class SwitchSender(val item: MenuItem, @StringRes val menuItemTitle: Int) : MainViewState()

    object ShowError : MainViewState()
}