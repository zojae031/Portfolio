package zojae031.portfolio.data.datasource.local

import io.reactivex.Maybe
import zojae031.portfolio.data.RepositoryImpl

interface LocalDataSource {
    fun getData(type: RepositoryImpl.ParseData): Maybe<String>

    fun insertData(type: RepositoryImpl.ParseData, data: String)

    fun deleteData(type: RepositoryImpl.ParseData, data: String)

}