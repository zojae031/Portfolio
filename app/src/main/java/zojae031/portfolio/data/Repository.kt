package zojae031.portfolio.data

import io.reactivex.Single
import zojae031.portfolio.data.dao.BasicEntity
import zojae031.portfolio.data.dao.CompetitionEntity

interface Repository {
    fun getBasicData(): Single<String>
    fun insertBasicData(data: BasicEntity)
    fun getCompetitionData(): Single<String>
    fun insertCompetitionData(data: Array<CompetitionEntity>)
}