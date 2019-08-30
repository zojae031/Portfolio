package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single

interface RemoteDataSource {

    var isDirty: MutableList<Boolean>
    fun getBasicData(): Single<String>
    fun getProjectData(): Single<String>
    fun getTecData(): Single<String>
}