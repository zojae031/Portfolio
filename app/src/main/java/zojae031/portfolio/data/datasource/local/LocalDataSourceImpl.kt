package zojae031.portfolio.data.datasource.local

import android.content.Context
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.dao.profile.BasicEntity
import zojae031.portfolio.data.dao.project.CompetitionEntity
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

class LocalDataSourceImpl private constructor(context: Context) : LocalDataSource {
    private val db = LocalDataBase.getInstance(context)
    private val basicDao = db.basicDao()
    private val projectDao = db.projectDao()
    private val tecDao = db.tecDao()
    override fun getData(type: RemoteDataSourceImpl.ParseData): Single<String> {
        return when (type) {
            RemoteDataSourceImpl.ParseData.PROFILE -> {
                getBasicData()
            }
            RemoteDataSourceImpl.ParseData.PROJECT -> {
                getProjectData()
            }
            RemoteDataSourceImpl.ParseData.TEC -> {
                getTecData()
            }
        }
    }

    override fun insertData(type: RemoteDataSourceImpl.ParseData, data: Any) {
        when (type) {
            RemoteDataSourceImpl.ParseData.PROFILE -> {
                basicDao.insert(data as BasicEntity)
            }
            RemoteDataSourceImpl.ParseData.PROJECT -> {
                projectDao.insert(data as CompetitionEntity)
            }
            RemoteDataSourceImpl.ParseData.TEC -> {
                tecDao.insert(data as TecEntity)
            }
        }
    }

    private fun getBasicData(): Single<String> {
        return Single.create(SingleOnSubscribe<String> { emitter ->
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
        }).subscribeOn(Schedulers.io())
    }


    private fun getProjectData(): Single<String> {
        return Single.create(SingleOnSubscribe<String> { emitter ->
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


        }).subscribeOn(Schedulers.io())
    }


    private fun getTecData(): Single<String> {
        return Single.create(SingleOnSubscribe<String> { emitter ->
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
        }).subscribeOn(Schedulers.io())
    }


    companion object {
        private var INSTANCE: LocalDataSourceImpl? = null
        fun getInstance(context: Context): LocalDataSourceImpl {
            if (INSTANCE == null) {
                INSTANCE =
                    LocalDataSourceImpl(context)
            }
            return INSTANCE!!
        }
    }
}