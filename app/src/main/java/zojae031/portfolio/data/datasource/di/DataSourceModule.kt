package zojae031.portfolio.data.datasource.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

val dataSourceModule = module {
    single<LocalDataSource> { LocalDataSourceImpl.getInstance(get()) }
    single<DataBase> { DataBase.getInstance(androidContext()) }
    single<RemoteDataSource> { RemoteDataSourceImpl.getInstance(get()) }
}