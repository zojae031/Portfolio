package zojae031.portfolio.data


import io.reactivex.Single

interface Repository {
    fun insertData(type: RepositoryImpl.ParseData, data: String)

    fun getData(type: RepositoryImpl.ParseData): Single<String>

}