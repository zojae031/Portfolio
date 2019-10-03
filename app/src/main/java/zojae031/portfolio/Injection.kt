package zojae031.portfolio

import android.content.Context
import android.net.ConnectivityManager
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl
import zojae031.portfolio.data.util.NetworkUtil

object Injection {
    private val urlList = listOf(
        "https://github.com/zojae031/Portfolio/issues/1",
        "https://github.com/zojae031/Portfolio/issues/2",
        "https://github.com/zojae031/Portfolio/issues/3",
        "https://github.com/zojae031/Portfolio/issues/4"
    )

    fun getRepository(context: Context): Repository =
        RepositoryImpl.getInstance(
            getLocalDataSource(context),
            getRemoteDataSource(urlList),
            getNetworkUtil(context)
        )

    fun getLocalDataSource(context: Context): LocalDataSource =
        LocalDataSourceImpl.getInstance(getDataBase(context))

    fun getDataBase(context: Context): DataBase = DataBase.getInstance(context)

    fun getRemoteDataSource(urlList: List<String>): RemoteDataSource =
        RemoteDataSourceImpl.getInstance(urlList)

    fun getConnectivityManager(context: Context): ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun getNetworkUtil(context: Context): NetworkUtil =
        NetworkUtil.getInstance(getConnectivityManager(context))
}
