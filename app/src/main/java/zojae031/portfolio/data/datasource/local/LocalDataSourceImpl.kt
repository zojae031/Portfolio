package zojae031.portfolio.data.datasource.local

import com.google.gson.JsonArray
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.util.DataConvertUtil

class LocalDataSourceImpl(db: DataBase) : LocalDataSource {
    private val basicDao = db.basicDao()
    private val projectDao = db.projectDao()
    private val tecDao = db.tecDao()
    private val mainDao = db.mainDao()

    override fun getProfile(): Maybe<ProfileEntity> {
        return basicDao.select()
    }

    override fun getProject(): Flowable<List<ProjectEntity>> {
        return projectDao.select().toFlowable()
    }

    override fun getData(type: RepositoryImpl.ParseData): Maybe<String> =
        when (type) {
            RepositoryImpl.ParseData.MAIN -> {
                mainDao.select().map { entity ->
                    DataConvertUtil.mainToJson(entity)
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                val array = JsonArray()
                tecDao.select().map { lists ->
                    lists.map { entity ->
                        array.add(DataConvertUtil.tecToJson(entity))
                    }
                }.map {
                    array.toString()
                }
            }
            else -> throw Exception("앙 기모쮜")
        }.subscribeOn(Schedulers.io())

    override fun insertProfile(data: ProfileEntity) {
        basicDao.insert(data)
    }

    override fun insertProject(data: ProjectEntity) {
        projectDao.insert(data)
    }

    override fun insertData(type: RepositoryImpl.ParseData, data: String) {
        when (type) {
            RepositoryImpl.ParseData.PROJECT -> {
                DataConvertUtil.stringToProjectList(data).also {
                    for (list in it) {
                        projectDao.insert(list)
                    }
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                DataConvertUtil.stringToTecList(data).also {
                    for (list in it) {
                        tecDao.insert(list)
                    }
                }
            }
            RepositoryImpl.ParseData.MAIN -> {
                DataConvertUtil.stringToMain(data).also { mainDao.insert(it) }
            }
            else -> throw Exception("앙 기모쮜")
        }
    }

    override fun deleteProfile(data: ProfileEntity) {
        basicDao.delete(data)
    }

    override fun deleteProject(data: ProjectEntity) {
        projectDao.delete(data)
    }

    override fun deleteData(type: RepositoryImpl.ParseData, data: String) {
        when (type) {
            RepositoryImpl.ParseData.PROJECT -> {
                DataConvertUtil.stringToProjectList(data).also {
                    for (list in it) {
                        projectDao.delete(list)
                    }
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                DataConvertUtil.stringToTecList(data).also {
                    for (list in it) {
                        tecDao.delete(list)
                    }
                }
            }
            RepositoryImpl.ParseData.MAIN -> {
                DataConvertUtil.stringToMain(data).also { mainDao.delete(it) }
            }
            else -> throw Exception("앙 기모취")
        }
    }

}