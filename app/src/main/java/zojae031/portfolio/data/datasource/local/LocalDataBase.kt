package zojae031.portfolio.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zojae031.portfolio.data.dao.BasicDao
import zojae031.portfolio.data.dao.BasicEntity
import zojae031.portfolio.data.dao.CompetitionEntity
import zojae031.portfolio.data.dao.ProjectDao

@Database(entities = [BasicEntity::class,CompetitionEntity::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun basicDao(): BasicDao
    abstract fun projectDao(): ProjectDao

    companion object {
        private var INSTANCE: LocalDataBase? = null
        fun getInstance(context: Context): LocalDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, LocalDataBase::class.java, "db").build()
            }
            return INSTANCE!!
        }
    }
}