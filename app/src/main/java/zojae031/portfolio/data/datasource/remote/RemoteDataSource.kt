package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single

interface RemoteDataSource {

    var isDirty: MutableList<Boolean>
    fun getBasicData(): Single<String>
    fun getCompetitionData(): Single<String>
}