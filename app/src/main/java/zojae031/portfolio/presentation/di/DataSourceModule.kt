package zojae031.portfolio.presentation.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

val dataSourceModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}