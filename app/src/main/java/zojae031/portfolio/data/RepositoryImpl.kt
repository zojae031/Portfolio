package zojae031.portfolio.data

import android.util.Log
import io.reactivex.Flowable
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.util.NetworkUtil

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val network: NetworkUtil
) : Repository {

    override fun getData(type: ParseData): Flowable<String> {
        Log.e("connect", network.isConnect.toString())
        return if (network.isConnect) {//기본 네트워크 살아있니?
            Flowable.concat(
                localDataSource.getData(type).toFlowable().doOnNext {
                    localDataSource.deleteData(type, it)
                },
                remoteDataSource.getData(type).doOnNext {
                    localDataSource.insertData(type, it)
                }
            )
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
            network: NetworkUtil
        ): RepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = RepositoryImpl(localDataSource, remoteDataSource, network)
            }
            return INSTANCE!!
        }
    }
}
