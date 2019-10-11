package zojae031.portfolio

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl
import zojae031.portfolio.util.NetworkUtil
import zojae031.portfolio.util.UrlUtil

object Injection {

    fun getRepository(context: Context): Repository =
        RepositoryImpl.getInstance(
            getLocalDataSource(context),
            getRemoteDataSource(getUrlUtil(context.applicationContext)),
            getNetworkUtil(context)
        )

    fun getLocalDataSource(context: Context): LocalDataSource =
        LocalDataSourceImpl.getInstance(getDataBase(context))

    fun getDataBase(context: Context): DataBase = DataBase.getInstance(context)

    fun getRemoteDataSource(urlUtil: UrlUtil): RemoteDataSource =
        RemoteDataSourceImpl.getInstance(urlUtil)

    fun getConnectivityManager(context: Context): ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun getNetworkUtil(context: Context): NetworkUtil =
        NetworkUtil.getInstance(getConnectivityManager(context))

    fun getUrlUtil(context: Context): UrlUtil = UrlUtil.getInstance(getSharedPreference(context))

    fun getSharedPreference(context: Context) =
        context.applicationContext.getSharedPreferences("pref", MODE_PRIVATE)

}
