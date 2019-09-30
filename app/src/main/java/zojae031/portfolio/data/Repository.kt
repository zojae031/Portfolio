package zojae031.portfolio.data


import io.reactivex.Single

interface Repository {

    fun getData(type: RepositoryImpl.ParseData): Single<String>

}