package zojae031.portfolio.data.dao.project

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface ProjectDao : BaseDao<ProjectEntity> {
    @Query("select * from ProjectEntity")
    fun select(): Maybe<List<ProjectEntity>>
}