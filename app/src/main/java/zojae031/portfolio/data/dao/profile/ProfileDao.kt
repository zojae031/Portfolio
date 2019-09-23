package zojae031.portfolio.data.dao.profile

import androidx.room.Dao
import androidx.room.Query
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface ProfileDao : BaseDao<ProfileEntity> {
    @Query("Select * from ProfileEntity")
    fun select(): List<ProfileEntity>
}