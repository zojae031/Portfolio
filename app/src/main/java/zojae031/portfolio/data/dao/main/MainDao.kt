package zojae031.portfolio.data.dao.main

import androidx.room.Dao
import androidx.room.Query
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface MainDao : BaseDao<MainEntity> {
    @Query("select * from MainEntity")
    fun select(): List<MainEntity>
}