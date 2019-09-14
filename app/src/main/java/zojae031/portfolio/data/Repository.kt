package zojae031.portfolio.data


import io.reactivex.Single
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

interface Repository {
    fun insertData(type: RemoteDataSourceImpl.ParseData, data: Any)

    fun getData(type: RemoteDataSourceImpl.ParseData): Single<String>

}