package zojae031.portfolio.base

import android.app.Application
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import zojae031.portfolio.data.datasource.di.dataSourceModule
import zojae031.portfolio.data.di.repositoryModule
import zojae031.portfolio.di.viewModelModule
import zojae031.portfolio.util.di.utilModule
import java.io.IOException
import java.net.SocketException


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(repositoryModule, dataSourceModule, utilModule, viewModelModule))
        }
    }

    private fun settingRxPlugIn() {
        RxJavaPlugins.setErrorHandler {
            var error = it
            if (error is UndeliverableException) {
                error = it.cause
            }
            if (error is IOException || error is SocketException) {
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            Log.d(
                "RxJavaPluginError",
                "Undeliverable Exception received, not sure what to do",
                error
            )
        }
    }
}
