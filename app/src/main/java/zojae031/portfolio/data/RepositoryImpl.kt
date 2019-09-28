package zojae031.portfolio.data

import android.net.ConnectivityManager
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val manager: ConnectivityManager
) : Repository {
    override fun getData(type: ParseData): Flowable<String> {
        return Single.concat(
            localDataSource.getData(type),
            remoteDataSource.getData(type).doOnSuccess { insertData(type, it) })
    }

    override fun insertData(type: ParseData, data: String) {
        Observable.fromCallable { localDataSource.insertData(type, data) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.e("fromCallable", "앙")
            }
    }


    enum class ParseData {
        PROFILE, PROJECT, TEC, USER_IMAGE
    }

    companion object {
        private var INSTANCE: RepositoryImpl? = null
        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource,
            manager: ConnectivityManager
        ): RepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = RepositoryImpl(localDataSource, remoteDataSource, manager)
            }
            return INSTANCE!!
        }
    }
}
