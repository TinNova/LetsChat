package tin.novakovic.letschat.ui

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tin.novakovic.domain.MessageHelper
import tin.novakovic.letschat.DisposingViewModel
import tin.novakovic.letschat.ui.MainViewState.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val messageHelper: MessageHelper) :
    DisposingViewModel() {

    private var isSender = true

    val viewState = MutableLiveData<MainViewState>()

    init {
        fetchAllMessage()
    }

    private fun fetchAllMessage() {
        add(messageHelper.fetchAllMessages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.value = ShowAllMessages(it)
                }, {
                    viewState.value = ShowError
                }
            ))
    }

    fun onSendClicked(message: String?) {
        message?.let { insertMessage(message) }
    }

    fun onSwitchClicked(isChecked: Boolean) {
        isSender = isChecked
    }

    private fun insertMessage(message: String) {
        add(messageHelper.insertMessage(message, isSender)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    fetchLatestMessage()
                }, {
                    it //show Toast
                }
            ))
    }

    private fun fetchLatestMessage() {
        add(messageHelper.fetchLatestMessage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.value = ShowLatestMessage(it)
                }, {
                    viewState.value = ShowError
                }
            ))
    }
}