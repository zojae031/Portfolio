package zojae031.portfolio.util.di

import android.content.Context
import android.net.ConnectivityManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import zojae031.portfolio.util.NetworkUtil
import zojae031.portfolio.util.UrlUtil

val utilModule = module {
    single {
        UrlUtil(
            androidContext().getSharedPreferences(
                "pref",
                Context.MODE_PRIVATE
            )
        )
    }
    single { NetworkUtil(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }
}