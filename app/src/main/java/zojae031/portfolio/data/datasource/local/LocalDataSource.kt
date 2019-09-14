package zojae031.portfolio.data.datasource.local

import io.reactivex.Single
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

interface LocalDataSource {
    fun getData(type: RemoteDataSourceImpl.Data): Single<String>

    fun insertData(type: RemoteDataSourceImpl.Data, data: Any)

}