package zojae031.portfolio.data.datasource.local

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
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


    override fun insertData(type: RepositoryImpl.ParseData, data: String) {
        when (type) {
            RepositoryImpl.ParseData.PROFILE -> {
                JsonParser().parse(data).asJsonObject.run {
                    Gson().fromJson(this, ProfileEntity::class.java)
                }.also { basicDao.insert(it) }
            }
            RepositoryImpl.ParseData.PROJECT -> {
                JsonParser().parse(data).asJsonArray.run {
                    Gson().fromJson(this, Array<ProjectEntity>::class.java)
                }.also {
                    for (list in it) {
                        projectDao.insert(list)
                    }
                }
            }
            RepositoryImpl.ParseData.TEC -> {
                JsonParser().parse(data).asJsonArray.run {
                    this.map {
                        return@map it.asJsonObject.run {
                            TecEntity(
                                get("name").asString,
                                get("image").asString,
                                get("source").toString()
                            )
                        }
                    }.toTypedArray()
                }.also {
                    for (list in it) {
                        tecDao.insert(list)
                    }
                }
            }
            RepositoryImpl.ParseData.USER_IMAGE -> {
                JsonParser().parse(data).asJsonObject.run {
                    Gson().fromJson(this, MainEntity::class.java)
                }.also { userDao.insert(it) }


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