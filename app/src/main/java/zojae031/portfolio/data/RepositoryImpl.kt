package zojae031.portfolio.data

import android.util.Log
import io.reactivex.Maybe
import io.reactivex.Single
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override fun getData(type: ParseData): Single<String> {
        return Maybe.concat(
            localDataSource.getData(type),
            remoteDataSource.getData(type).doOnSuccess {
                Log.e("insertData", it)
                localDataSource.insertData(type, it)
            })
            .filter { it != "[]" }.firstOrError()
    }

    enum class ParseData {
        PROFILE, PROJECT, TEC, MAIN
    }

    companion object {
        private var INSTANCE: RepositoryImpl? = null
        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource
        ): RepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = RepositoryImpl(localDataSource, remoteDataSource)
            }
            return INSTANCE!!
        }
    }
}
