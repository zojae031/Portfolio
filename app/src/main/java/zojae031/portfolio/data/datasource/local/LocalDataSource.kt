package zojae031.portfolio.data.datasource.local

import io.reactivex.Single
import zojae031.portfolio.data.dao.BasicEntity
import zojae031.portfolio.data.dao.CompetitionEntity

interface LocalDataSource {
    fun getBasicData(): Single<String>
    fun insertBasicData(data: BasicEntity)

    fun getProjectData(): Single<String>
    fun insertProjectData(data: CompetitionEntity)
}