package zojae031.portfolio.data.datasource.local

import io.reactivex.Maybe
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.data.datasource.DataBase

class LocalDataSourceImpl(db: DataBase) : LocalDataSource {
    private val basicDao = db.basicDao()
    private val projectDao = db.projectDao()
    private val tecDao = db.tecDao()
    private val mainDao = db.mainDao()

    override fun getProfile(): Maybe<ProfileEntity> = basicDao.select()

    override fun getProject(): Maybe<List<ProjectEntity>> = projectDao.select()

    override fun getTec(): Maybe<List<TecEntity>> = tecDao.select()

    override fun getMainData(): Maybe<MainEntity> = mainDao.select()

    override fun insertProfile(data: ProfileEntity) {
        basicDao.insert(data)
    }

    override fun insertProject(data: ProjectEntity) {
        projectDao.insert(data)
    }

    override fun insertTec(data: TecEntity) {
        tecDao.insert(data)
    }

    override fun insertMain(data: MainEntity) {
        mainDao.insert(data)
    }

    override fun deleteProfile(data: ProfileEntity) {
        basicDao.delete(data)
    }

    override fun deleteProject(data: ProjectEntity) {
        projectDao.delete(data)
    }

    override fun deleteTec(data: TecEntity) {
        tecDao.delete(data)
    }

    override fun deleteMain(data: MainEntity) {
        mainDao.delete(data)
    }

}