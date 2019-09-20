package zojae031.portfolio.data.datasource.local

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.BasicEntity
import zojae031.portfolio.data.dao.project.CompetitionEntity
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.data.datasource.DataBase

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
            RepositoryImpl.ParseData.USER_IMAGE -> {
                getMainData()
            }
        }.subscribeOn(Schedulers.io())


    override fun insertData(type: RepositoryImpl.ParseData, data: Any) {
        when (type) {
            RepositoryImpl.ParseData.PROFILE -> {
                basicDao.insert(data as BasicEntity)
            }
            RepositoryImpl.ParseData.PROJECT -> {
                projectDao.insert(data as CompetitionEntity)
            }
            RepositoryImpl.ParseData.TEC -> {
                tecDao.insert(data as TecEntity)
            }
            RepositoryImpl.ParseData.USER_IMAGE -> {
                userDao.insert(data as MainEntity)
            }
        }
    }

    private fun getBasicData(): Single<String> =
        Single.create { emitter ->
            basicDao.select().map {
                JsonObject().apply {
                    addProperty("name", it.name)
                    addProperty("age", it.age)
                    addProperty("university", it.university)
                    addProperty("major", it.major)
                    addProperty("military", it.military)
                    addProperty("hobby", it.hobby)
                    addProperty("additional", it.additional)
                }.also { emitter.onSuccess(it.toString()) }
            }
        }


    private fun getProjectData(): Single<String> =
        Single.create { emitter ->
            val array = JsonArray()
            projectDao.select().map {
                JsonObject().apply {
                    addProperty("image", it.image)
                    addProperty("name", it.name)
                    addProperty("competition", it.competition)
                    addProperty("prize", it.prize)
                    addProperty("text", it.text)
                    addProperty("video", it.video)
                    addProperty("skills", it.skills)
                    addProperty("git", it.git)
                    addProperty("date", it.date)
                }
            }.map {
                array.add(it)
            }.also {
                emitter.onSuccess(array.toString())
            }
        }


    private fun getTecData(): Single<String> =
        Single.create { emitter ->
            val arr = JsonArray()
            tecDao.select().map {
                JsonObject().apply {
                    addProperty("name", it.name)
                    addProperty("image", it.image)
                    add("source", JsonParser().parse(it.source).asJsonArray)
                }
            }.also {
                arr.add(it.toString())
            }
            emitter.onSuccess(arr.asString)
        }

    private fun getMainData(): Single<String> =
        Single.create { emitter ->
            userDao.select().map { entity ->
                JsonObject().apply {
                    addProperty("userImage", entity.userImage)
                    addProperty("notice", entity.notice)
                }.also { emitter.onSuccess(it.toString()) }
            }
        }

    companion object {
        private var INSTANCE: LocalDataSourceImpl? = null
        fun getInstance(db: DataBase): LocalDataSourceImpl {
            if (INSTANCE == null) {
                INSTANCE =
                    LocalDataSourceImpl(db)
            }
            return INSTANCE!!
        }
    }
}