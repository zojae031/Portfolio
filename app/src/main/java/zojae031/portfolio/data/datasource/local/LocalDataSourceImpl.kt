package zojae031.portfolio.data.datasource.local

import android.content.Context
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.dao.profile.BasicEntity
import zojae031.portfolio.data.dao.project.CompetitionEntity

class LocalDataSourceImpl private constructor(context: Context) : LocalDataSource {
    private val db = LocalDataBase.getInstance(context)
    private val basicDao = db.basicDao()
    private val projectDao = db.projectDao()

    override fun getBasicData(): Single<String> {
        return Single.create(SingleOnSubscribe<String> { emitter ->
            val data = basicDao.select()
            JsonObject().apply {
                addProperty("name", data[0].name)
                addProperty("age", data[0].age)
                addProperty("university", data[0].university)
                addProperty("major", data[0].major)
                addProperty("military", data[0].military)
                addProperty("hobby", data[0].hobby)
                addProperty("additional", data[0].additional)
            }.also { emitter.onSuccess(it.toString()) }

        }).subscribeOn(Schedulers.io())
    }

    override fun insertBasicData(data: BasicEntity) {
        basicDao.insert(data)
    }

    override fun getProjectData(): Single<String> {
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
                }
            }.map {
                array.add(it)
            }.also {
                emitter.onSuccess(array.toString())
            }


        }).subscribeOn(Schedulers.io())
    }

    override fun insertProjectData(data: CompetitionEntity) {
        projectDao.insert(data)
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