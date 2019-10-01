package zojae031.portfolio.data


import io.reactivex.Flowable

interface Repository {

    fun getData(type: RepositoryImpl.ParseData): Flowable<String>

}