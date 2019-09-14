package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single

interface RemoteDataSource {

    var isDirty: MutableList<Boolean>

    fun getData(type: RemoteDataSourceImpl.Data): Single<String>
}