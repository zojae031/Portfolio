package zojae031.portfolio.data.dao.tec

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import zojae031.portfolio.data.dao.BaseDao

@Dao
interface TecDao : BaseDao<TecEntity> {
    @Query("select * from TecEntity")
    fun select(): Maybe<List<TecEntity>>
}