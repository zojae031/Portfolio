package zojae031.portfolio.data


import io.reactivex.Flowable

interface Repository {
    fun insertData(type: RepositoryImpl.ParseData, data: String)

    fun getData(type: RepositoryImpl.ParseData): Flowable<String>

}