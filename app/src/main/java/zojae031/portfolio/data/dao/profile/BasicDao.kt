package zojae031.portfolio.data.dao.profile

import androidx.room.Dao
import androidx.room.Query
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface BasicDao : BaseDao<BasicEntity> {
    @Query("Select * from BasicEntity")
    fun select(): List<BasicEntity>
}