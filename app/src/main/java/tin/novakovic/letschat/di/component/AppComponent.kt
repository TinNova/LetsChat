package tin.novakovic.letschat.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import tin.novakovic.letschat.ui.MainActivity
import tin.novakovic.letschat.di.modules.AppModule
import tin.novakovic.letschat.di.modules.RoomModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

}