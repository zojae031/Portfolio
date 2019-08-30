package zojae031.portfolio.data

import io.reactivex.Single
import zojae031.portfolio.data.dao.profile.BasicEntity


import zojae031.portfolio.data.dao.project.CompetitionEntity
import zojae031.portfolio.data.dao.tec.TecEntity

interface Repository {
    fun getBasicData(): Single<String>
    fun insertBasicData(data: BasicEntity)
    fun getCompetitionData(): Single<String>
    fun insertCompetitionData(data: Array<CompetitionEntity>)
    fun getTecData(): Single<String>
    fun insertTecData(data: Array<TecEntity>)
}