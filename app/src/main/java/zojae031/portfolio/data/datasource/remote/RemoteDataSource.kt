package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single
import zojae031.portfolio.data.RepositoryImpl

interface RemoteDataSource {

    var isDirty: MutableList<Boolean>

    fun getData(type: RepositoryImpl.ParseData): Single<String>
}