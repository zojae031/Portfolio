package zojae031.portfolio.data.dao.main

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface MainUserDao : BaseDao<MainUserEntity> {
    @Query("select * from MainUserEntity")
    fun select(): Maybe<MainUserEntity>
}