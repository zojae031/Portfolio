package zojae031.portfolio.data.datasource.remote

import io.reactivex.Flowable
import io.reactivex.Single
import zojae031.portfolio.data.RepositoryImpl

interface RemoteDataSource {
    fun getUserList(): Single<List<String>>
    fun getErrorList(): Single<List<String>>
    fun getData(type: RepositoryImpl.ParseData): Flowable<String>
}