package tin.novakovic.letschat.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import tin.novakovic.letschat.MainActivity
import tin.novakovic.letschat.di.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

}