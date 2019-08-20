package zojae031.portfolio.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T)
}