package zojae031.portfolio.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProjectDao : BaseDao<CompetitionEntity> {
    @Query("select * from CompetitionEntity")
    fun select(): List<CompetitionEntity>
}