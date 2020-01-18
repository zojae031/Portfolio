package zojae031.portfolio.data.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity


object DataConvertUtil {
    fun stringToMain(data: String): MainEntity {
        return JsonParser().parse(data).asJsonObject.run {
            Gson().fromJson(this, MainEntity::class.java)
        }
    }

    fun stringToProfile(data: String): ProfileEntity {
        return JsonParser().parse(data).asJsonObject.run {
            Gson().fromJson(this, ProfileEntity::class.java)
        }
    }

    fun stringToProjectList(data: String): List<ProjectEntity> {
        return JsonParser().parse(data).asJsonArray.run {
            Gson().fromJson(this, Array<ProjectEntity>::class.java)
        }.asList()
    }


    fun stringToTecList(data: String): List<TecEntity> {
        return JsonParser().parse(data).asJsonArray.run {
            this.map { element ->
                return@map element.asJsonObject.run {
                    TecEntity(
                        get("name").asString,
                        get("image").asString,
                        get("source").toString()
                    )
                }
            }.toList()
        }
    }


    fun mainToJson(data: MainEntity) = JsonObject().apply {
        addProperty("userImage", data.userImage)
        addProperty("notice", data.notice)
    }.toString()

    fun profileToJson(data: ProfileEntity) =
        JsonObject().apply {
            addProperty("name", data.name)
            addProperty("age", data.age)
            addProperty("university", data.university)
            addProperty("major", data.major)
            addProperty("military", data.military)
            addProperty("hobby", data.hobby)
            addProperty("additional", data.additional)
        }.toString()

    fun projectToJson(data: ProjectEntity) =
        JsonObject().apply {
            addProperty("image", data.image)
            addProperty("name", data.name)
            addProperty("competition", data.competition)
            addProperty("prize", data.prize)
            addProperty("text", data.text)
            addProperty("video", data.video)
            addProperty("skills", data.skills)
            addProperty("git", data.git)
            addProperty("date", data.date)
        }

    fun tecToJson(data: TecEntity) = JsonObject().apply {
        addProperty("name", data.name)
        addProperty("image", data.image)
        add("source", JsonParser().parse(data.source).asJsonArray)
    }

}