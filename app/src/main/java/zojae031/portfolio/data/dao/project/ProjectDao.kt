package zojae031.portfolio.data.dao.project

import androidx.room.Dao
import androidx.room.Query
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface ProjectDao : BaseDao<CompetitionEntity> {
    @Query("select * from CompetitionEntity")
    fun select(): List<CompetitionEntity>
}