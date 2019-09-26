package zojae031.portfolio.data


import io.reactivex.Flowable

interface Repository {
    fun <T> insertData(type: RepositoryImpl.ParseData, data: T)

    fun getData(type: RepositoryImpl.ParseData): Flowable<String>

}