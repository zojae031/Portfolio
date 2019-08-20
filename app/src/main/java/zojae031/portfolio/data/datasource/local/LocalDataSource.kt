package zojae031.portfolio.data.datasource.local

import io.reactivex.Single
import zojae031.portfolio.data.dao.BasicEntity
import zojae031.portfolio.data.dao.CompetitionEntity

interface LocalDataSource {
    fun getBasicInformation(): Single<String>
    fun insertBasicInformation(data: BasicEntity)

    fun getProjectInformation(): Single<String>
    fun insertProjectInformation(data: CompetitionEntity)
}