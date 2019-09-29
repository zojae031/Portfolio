package zojae031.portfolio.data.datasource.local

import com.google.gson.JsonArray
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.util.DataConvertUtil

class LocalDataSourceImpl private constructor(db: DataBase) : LocalDataSource {
    private val basicDao = db.basicDao()
    private val projectDao = db.projectDao()
    private val tecDao = db.tecDao()
    private val userDao = db.userDao()

    override fun getData(type: RepositoryImpl.ParseData): Single<String> =
        when (type) {
            RepositoryImpl.ParseData.PROFILE -> {
                getBasicData()
            }
            RepositoryImpl.ParseData.PROJECT -> {
                getProjectData()
            }
            RepositoryImpl.ParseData.TEC -> {
                getTecData()
            }
            RepositoryImpl.ParseData.MAIN -> {
                getMainData()
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
                DataConvertUtil.stringToMain(data).also { userDao.insert(it) }


            }
        }
    }

    private fun getBasicData(): Single<String> =
        Single.create { emitter ->
            basicDao
                .select()
                .map { entity ->
                DataConvertUtil.profileToJson(entity)
                    .also { emitter.onSuccess(it.toString()) }
            }
        }


    private fun getProjectData(): Single<String> =
        Single.create { emitter ->
            val array = JsonArray()
            projectDao.select().map { entity ->
                DataConvertUtil.projectToJson(entity)
            }.map {
                array.add(it)
            }.also {
                emitter.onSuccess(array.toString())
            }
        }


    private fun getTecData(): Single<String> =
        Single.create { emitter ->
            val arr = JsonArray()
            tecDao.select().map { entity ->
                DataConvertUtil.tecToJson(entity)
            }.also {
                arr.add(it.toString())
            }
            emitter.onSuccess(arr.asString)
        }

    private fun getMainData(): Single<String> =
        Single.create { emitter ->
            userDao.select().map { entity ->
                DataConvertUtil.mainToJson(entity).also { emitter.onSuccess(it.toString()) }
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