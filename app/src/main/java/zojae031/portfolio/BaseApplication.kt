package zojae031.portfolio

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi

class BaseApplication : Application() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()
        Injection.getNetworkUtil(applicationContext).checkNetworkInfo()
    }
}