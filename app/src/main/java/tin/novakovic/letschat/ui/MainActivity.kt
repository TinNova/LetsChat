package tin.novakovic.letschat.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tin.novakovic.letschat.App
import tin.novakovic.letschat.R
import tin.novakovic.letschat.ViewModelFactory
import tin.novakovic.letschat.ui.MainViewState.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<MainViewModel>
    private lateinit var viewModel: MainViewModel
    private val adapter = MainAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        initView()
    }

    private fun initView() {
        observeViewState()
        main_recycler_view.adapter = adapter
        main_recycler_view.layoutManager

        var l = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        l.stackFromEnd = true

        main_recycler_view.layoutManager = l

        main_send_button.setOnClickListener {
            viewModel.onSendClicked(main_edit_text.text.toString())
        }

    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer {
            when (it) {
                is ShowAllMessages -> adapter.addMessages(it.messages)
                is ShowLatestMessage -> adapter.addLatestMessage(it.message)
                is UpdatePreviousMessage -> adapter.updatePreviousMessage(it.message)
                is AddTimeStamp -> adapter.addTimeStamp(it.message)
            }
            scrollToBottomOfRecyclerView()
        })

    }

    private fun scrollToBottomOfRecyclerView() {
        main_recycler_view.smoothScrollToPosition(adapter.itemCount)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        val item = menu.findItem(R.id.switch_item)
        item.setActionView(R.layout.switch_menu_item)

        val switchOnOff: SwitchCompat = item.actionView.findViewById(R.id.switch_icon)
        switchOnOff.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.onSwitchClicked(isChecked)
        }
        return true
    }

}
