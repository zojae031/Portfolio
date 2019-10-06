package zojae031.portfolio

import android.app.Application

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injection.getNetworkUtil(applicationContext).checkNetworkInfo()
    }
}