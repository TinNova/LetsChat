package tin.novakovic.letschat.ui

import tin.novakovic.domain.MessageModel

sealed class MainViewState {

    data class ShowLatestMessage(val message: MessageModel) : MainViewState()

    data class ShowAllMessages(val messages: List<MessageModel>) : MainViewState()

    data class UpdatePreviousMessage(val message: MessageModel) : MainViewState()

    data class AddTimeStamp(val message: MessageModel) : MainViewState()

    object ShowError : MainViewState()
}