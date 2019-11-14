package zojae031.portfolio.data.datasource.remote

import com.google.gson.JsonObject
import io.reactivex.Single
import org.jsoup.Connection
import org.jsoup.Jsoup
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.util.UrlUtil

class RemoteDataSourceImpl(private val urlUtil: UrlUtil) : RemoteDataSource {

    override fun getUserList(): Single<List<String>> = Single.create { emitter ->
        try {
            Jsoup.connect(urlUtil.getUserListUrl())
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

    override fun getData(type: RepositoryImpl.ParseData): Single<String> =
        Single.create { emitter ->
            try {
                Jsoup.connect(urlUtil.urlList[type.ordinal])
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