package tin.novakovic.letschat

import android.app.Application
import tin.novakovic.letschat.di.component.AppComponent
import tin.novakovic.letschat.di.component.DaggerAppComponent

open class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}