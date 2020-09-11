package tin.novakovic.letschat

import android.app.Application
import tin.novakovic.letschat.di.component.AppComponent
import tin.novakovic.letschat.di.component.DaggerAppComponent
import tin.novakovic.letschat.di.modules.AppModule
import tin.novakovic.letschat.di.modules.RoomModule

class App : Application() {

    val component: AppComponent = DaggerAppComponent
        .builder()
        .roomModule(RoomModule(this))
        .build()

}