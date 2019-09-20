package zojae031.portfolio.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zojae031.portfolio.data.dao.main.MainDao
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.BasicDao
import zojae031.portfolio.data.dao.profile.BasicEntity
import zojae031.portfolio.data.dao.project.CompetitionEntity
import zojae031.portfolio.data.dao.project.ProjectDao
import zojae031.portfolio.data.dao.tec.TecDao
import zojae031.portfolio.data.dao.tec.TecEntity

@Database(
    entities = [BasicEntity::class, CompetitionEntity::class, TecEntity::class, MainEntity::class],
    version = 2
)
abstract class DataBase : RoomDatabase() {
    abstract fun basicDao(): BasicDao
    abstract fun projectDao(): ProjectDao
    abstract fun tecDao(): TecDao
    abstract fun userDao(): MainDao

    companion object {
        private var INSTANCE: DataBase? = null
        fun getInstance(context: Context): DataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}