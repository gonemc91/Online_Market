import android.app.Application
import com.em.common.Core
import com.em.common.CoreProvider

class App: Application() {

    lateinit var coreProvider: CoreProvider

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
    }
}