package zojae031.portfolio.base

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import zojae031.portfolio.Injection

class BaseApplication : Application() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()
        Injection.getNetworkUtil(applicationContext).checkNetworkInfo()
    }
}