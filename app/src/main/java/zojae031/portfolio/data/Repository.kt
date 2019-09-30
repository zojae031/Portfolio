package zojae031.portfolio.data


import io.reactivex.Maybe

interface Repository {

    fun getData(type: RepositoryImpl.ParseData): Maybe<String>

}