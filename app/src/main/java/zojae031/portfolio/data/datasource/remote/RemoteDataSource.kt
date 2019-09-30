package zojae031.portfolio.data.datasource.remote

import io.reactivex.Maybe
import zojae031.portfolio.data.RepositoryImpl

interface RemoteDataSource {
    fun getData(type: RepositoryImpl.ParseData): Maybe<String>
}