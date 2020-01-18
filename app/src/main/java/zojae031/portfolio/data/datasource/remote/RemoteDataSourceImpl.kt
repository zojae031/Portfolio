package zojae031.portfolio.data.datasource.remote

import com.google.gson.JsonObject
import io.reactivex.Single
import org.jsoup.Connection
import org.jsoup.Jsoup
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.util.DataConvertUtil
import zojae031.portfolio.util.UrlHelper

class RemoteDataSourceImpl(private val urlHelper: UrlHelper) : RemoteDataSource {

    override fun getProfile(): Single<ProfileEntity> =
        parsing(RepositoryImpl.ParseData.PROFILE).map { data ->
            DataConvertUtil.stringToProfile(data)
        }


    override fun getUserList(): Single<List<String>> = Single.create { emitter ->
        try {
            Jsoup.connect(urlHelper.getUserListUrl())
                .method(Connection.Method.GET)
                .execute()
                .apply {
                    this.parse().select(".network").select(".repo").select(".gravatar").also {
                        it.map { element ->
                            JsonObject().apply {
                                addProperty("images", element.attr("src"))
                                addProperty("name", element.attr("alt"))
                            }.toString()
                        }.also { list ->
                            emitter.onSuccess(list)
                        }
                    }
                }
        } catch (e: Exception) {
            emitter.tryOnError(e)
        } catch (t: Throwable) {
            emitter.tryOnError(t)
        }
    }

    override fun getProject(): Single<List<ProjectEntity>> {
        return parsing(RepositoryImpl.ParseData.PROJECT).map { data ->
            DataConvertUtil.stringToProjectList(data)
        }
    }

    override fun getTec(): Single<List<TecEntity>> {
        return parsing(RepositoryImpl.ParseData.TEC).map { data ->
            DataConvertUtil.stringToTecList(data)
        }
    }

    override fun getData(type: RepositoryImpl.ParseData): Single<String> =
        Single.create { emitter ->
            try {
                Jsoup.connect(urlHelper.urlList[type.ordinal])
                    .method(Connection.Method.GET)
                    .execute()
                    .apply {
                        this.parse().select(".Box-body").select("tbody").text().also {
                            emitter.onSuccess(it)
                        }
                    }
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }

    private fun parsing(type: RepositoryImpl.ParseData) =
        Single.create<String> { emitter ->
            try {
                Jsoup.connect(urlHelper.urlList[type.ordinal])
                    .method(Connection.Method.GET)
                    .execute()
                    .apply {
                        this.parse().select(".Box-body").select("tbody").text().also {
                            emitter.onSuccess(it)
                        }
                    }
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }

}