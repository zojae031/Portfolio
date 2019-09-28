package zojae031.portfolio.data.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity

object DataConverUtil {
    fun StringToMainEntity(data: String): MainEntity {
        return JsonParser().parse(data).asJsonObject.run {
            Gson().fromJson(this, MainEntity::class.java)
        }
    }

    fun StringToProfileEntity(data: String): ProfileEntity {
        return JsonParser().parse(data).asJsonObject.run {
            Gson().fromJson(this, ProfileEntity::class.java)
        }
    }

    fun StringToProjectArray(data: String): Array<ProjectEntity> {
        return JsonParser().parse(data).asJsonArray.run {
            Gson().fromJson(this, Array<ProjectEntity>::class.java)
        }
    }

    fun StringToTecArray(data: String): Array<TecEntity> {
        return JsonParser().parse(data).asJsonArray.run {
            this.map {
                return@map it.asJsonObject.run {
                    TecEntity(
                        get("name").asString,
                        get("image").asString,
                        get("source").toString()
                    )
                }
            }.toTypedArray()
        }
    }
}