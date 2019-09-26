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
            remoteDataSource.getData(type).doAfterSuccess { insert(type, it) })
    }

    override fun <T> insertData(type: ParseData, data: T) {
        if (remoteDataSource.isDirty[type.ordinal]) { //캐시가 더러울때만 저장
            when (type) {
                ParseData.PROFILE -> {
                    localDataSource.insertData(type, data)
                }
                ParseData.PROJECT -> {
                    for (list in data as Array<*>) {
                        localDataSource.insertData(type, list)
                    }
                }
                ParseData.TEC -> {
                    for (list in data as Array<*>) {
                        localDataSource.insertData(type, list)
                    }
                }
                ParseData.USER_IMAGE -> {
                    localDataSource.insertData(type, data)
                }
            }

        }
    }

    fun <T> insert(type: ParseData, data: T) {
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