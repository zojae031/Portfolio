package zojae031.portfolio.data


import io.reactivex.Single

interface Repository {
    fun <T> insertData(type: RepositoryImpl.ParseData, data: T)

    fun getData(type: RepositoryImpl.ParseData): Single<String>

}