package zojae031.portfolio.data.dao.user

import androidx.room.Dao
import androidx.room.Query
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface UserDao : BaseDao<UserEntity> {
    @Query("select * from UserEntity")
    fun select(): List<UserEntity>
}