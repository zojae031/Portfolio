package zojae031.portfolio.data

import android.net.ConnectivityManager
import io.reactivex.Flowable
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val manager: ConnectivityManager
) : Repository {
    override fun getData(type: ParseData): Flowable<String> {
        return if (!manager.isDefaultNetworkActive) {
            localDataSource.getData(type)
                .concatWith(remoteDataSource.getData(type).doOnSuccess {
                    localDataSource.insertData(type, it)
                }.toMaybe())
        } else {
            localDataSource.getData(type).toFlowable()
        }

    }

    enum class ParseData {
        PROFILE, PROJECT, TEC, MAIN
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
