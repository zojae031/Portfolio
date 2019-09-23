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

object Injection {

    fun getRepository(context: Context): Repository =
        RepositoryImpl.getInstance(
            getLocalDataSource(context),
            getRemoteDataSource(),
            getConnectivityManager(context)
        )

    fun getLocalDataSource(context: Context): LocalDataSource =
        LocalDataSourceImpl.getInstance(getDataBase(context))

    fun getDataBase(context: Context): DataBase = DataBase.getInstance(context)

    fun getRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl

    fun getConnectivityManager(context: Context): ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
