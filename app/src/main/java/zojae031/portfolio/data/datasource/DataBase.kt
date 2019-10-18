package zojae031.portfolio.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import zojae031.portfolio.data.dao.main.MainDao
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileDao
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectDao
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecDao
import zojae031.portfolio.data.dao.tec.TecEntity

@Database(
    entities = [ProfileEntity::class, ProjectEntity::class, TecEntity::class, MainEntity::class],
    version = 3
)
abstract class DataBase : RoomDatabase() {
    abstract fun basicDao(): ProfileDao
    abstract fun projectDao(): ProjectDao
    abstract fun tecDao(): TecDao
    abstract fun mainDao(): MainDao


}