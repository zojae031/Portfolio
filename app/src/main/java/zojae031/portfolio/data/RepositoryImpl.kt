package zojae031.portfolio.data

import android.net.ConnectivityManager
import io.reactivex.Single
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val manager: ConnectivityManager
) : Repository {
    override fun getData(type: RemoteDataSourceImpl.Data): Single<String> {
        return if (manager.activeNetwork != null) {//네트워크 연결상태 on
            if (remoteDataSource.isDirty[type.ordinal]) {//캐시가 지저분하면 로컬에서 땡겨옴
                localDataSource.getData(type)
            } else {
                remoteDataSource.getData(type)
            }
        } else {//네트워크 연결상태 off
            localDataSource.getData(type)
        }
    }

    override fun insertData(type: RemoteDataSourceImpl.Data, data: Any) {
        if (remoteDataSource.isDirty[type.ordinal]) { //캐시가 더러울때만 저장
            when (type) {
                RemoteDataSourceImpl.Data.PROFILE -> {
                    localDataSource.insertData(type, data)
                }
                RemoteDataSourceImpl.Data.PROJECT -> {
                    for (list in data as Array<*>) {
                        localDataSource.insertData(type, list!!)
                    }
                }
                RemoteDataSourceImpl.Data.TEC -> {
                    for (list in data as Array<*>) {
                        localDataSource.insertData(type, list!!)
                    }

                }
            }

        }
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