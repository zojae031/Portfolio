package zojae031.portfolio.data


import io.reactivex.Flowable
import io.reactivex.Single

interface Repository {
    fun getUserList(): Single<List<String>>
    fun getData(type: RepositoryImpl.ParseData): Flowable<String>

}