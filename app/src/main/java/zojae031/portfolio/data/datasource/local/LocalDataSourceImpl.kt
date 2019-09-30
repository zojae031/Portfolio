package zojae031.portfolio.data.datasource.local

import com.google.gson.JsonArray
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.util.DataConvertUtil

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
                projectDao.select().map { entity ->
                    array.add(DataConvertUtil.projectToJson(entity)) as String
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                val arr = JsonArray()
                tecDao.select().map { entity ->
                    arr.add(DataConvertUtil.tecToJson(entity)) as String
                }

            }
        }.subscribeOn(Schedulers.io())


    override fun insertData(type: RepositoryImpl.ParseData, data: String) {
        when (type) {
            RepositoryImpl.ParseData.PROFILE -> {
                DataConvertUtil.stringToProfile(data).also { basicDao.insert(it) }
            }
            RepositoryImpl.ParseData.PROJECT -> {
                DataConvertUtil.stringToProjectArray(data).also {
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