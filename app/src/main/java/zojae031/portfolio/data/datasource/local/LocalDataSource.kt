package zojae031.portfolio.data.datasource.local

import io.reactivex.Single
import zojae031.portfolio.data.RepositoryImpl

interface LocalDataSource {
    fun getData(type: RepositoryImpl.ParseData): Single<String>

    fun insertData(type: RepositoryImpl.ParseData, data: String)

}