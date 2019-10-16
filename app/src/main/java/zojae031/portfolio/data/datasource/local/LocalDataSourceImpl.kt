package zojae031.portfolio.data.datasource.local

import com.google.gson.JsonArray
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.util.DataConvertUtil

class LocalDataSourceImpl private constructor(db: DataBase) : LocalDataSource {
    private val basicDao = db.basicDao()
    private val projectDao = db.projectDao()
    private val tecDao = db.tecDao()
    private val mainDao = db.mainDao()

    override fun getData(type: RepositoryImpl.ParseData): Maybe<String> =
        when (type) {
            RepositoryImpl.ParseData.MAIN -> {
                mainDao.select().map { entity ->
                    DataConvertUtil.mainToJson(entity)
                }
            }
            RepositoryImpl.ParseData.PROFILE -> {
                basicDao
                    .select()
                    .map { entity ->
                        DataConvertUtil.profileToJson(entity)
                    }
            }
            RepositoryImpl.ParseData.PROJECT -> {
                val array = JsonArray()
                projectDao.select().map { lists ->
                    lists.map { entity ->
                        array.add(DataConvertUtil.projectToJson(entity))
                    }
                }.map {
                    array.toString()
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
        }.subscribeOn(Schedulers.io())


    override fun insertData(type: RepositoryImpl.ParseData, data: String) {
        when (type) {
            RepositoryImpl.ParseData.PROFILE -> {
                DataConvertUtil.stringToProfile(data).also { basicDao.insert(it) }
            }
            RepositoryImpl.ParseData.PROJECT -> {
                DataConvertUtil.stringToProjectList(data).also {
                    for (list in it) {
                        projectDao.insert(list)
                    }
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                DataConvertUtil.stringToTecArray(data).also {
                    for (list in it) {
                        tecDao.insert(list)
                    }
                }
            }
            RepositoryImpl.ParseData.MAIN -> {
                DataConvertUtil.stringToMain(data).also { mainDao.insert(it) }
            }
        }
    }

    override fun deleteData(type: RepositoryImpl.ParseData, data: String) {
        when (type) {
            RepositoryImpl.ParseData.PROFILE -> {
                DataConvertUtil.stringToProfile(data).also { basicDao.delete(it) }
            }
            RepositoryImpl.ParseData.PROJECT -> {
                DataConvertUtil.stringToProjectList(data).also {
                    for (list in it) {
                        projectDao.delete(list)
                    }
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                DataConvertUtil.stringToTecArray(data).also {
                    for (list in it) {
                        tecDao.delete(list)
                    }
                }
            }
            RepositoryImpl.ParseData.MAIN -> {
                DataConvertUtil.stringToMain(data).also { mainDao.delete(it) }
            }
        }
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(db: DataBase): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE =
                    LocalDataSourceImpl(db)
            }
            return INSTANCE!!
        }
    }
}