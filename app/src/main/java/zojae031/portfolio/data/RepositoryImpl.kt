package zojae031.portfolio.data

import io.reactivex.Maybe
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override fun getData(type: ParseData): Maybe<String> {
        return Maybe.concat(
            localDataSource.getData(type),
            remoteDataSource.getData(type).toMaybe().doOnSuccess { data ->
                localDataSource.insertData(
                    type,
                    data
                )
            }).first("first Data").filter { true }
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
