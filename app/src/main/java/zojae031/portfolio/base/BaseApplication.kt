package zojae031.portfolio.base

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import zojae031.portfolio.data.datasource.di.dataSourceModule
import zojae031.portfolio.data.di.repositoryModule
import zojae031.portfolio.di.viewModelModule
import zojae031.portfolio.util.NetworkUtil
import zojae031.portfolio.util.di.utilModule

class BaseApplication : Application() {
    private val networkUtil: NetworkUtil by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(repositoryModule, dataSourceModule, utilModule, viewModelModule))
            networkUtil.checkNetworkInfo()
        }
    }
}

//TODO
// 1. Repository 패턴 문제 해결
// 2. Tec 리팩토링