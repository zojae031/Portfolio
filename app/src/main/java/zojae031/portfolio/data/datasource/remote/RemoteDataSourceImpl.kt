package zojae031.portfolio.data.datasource.remote

import com.google.gson.JsonObject
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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
                            val src = element.attr("src")
                            val alt = element.attr("alt")
                            JsonObject().apply {
                                addProperty("images", src)
                                addProperty("name", alt)
                            }.toString()
                        }.also { list ->
                            emitter.onSuccess(list)
                        }
                    }
                }
        } catch (e: Exception) {
            emitter.tryOnError(e)
        }
    }

    override fun getErrorList(): Single<List<String>> {
        return Single.create {
            it.onSuccess(
                listOf(
                    JsonObject().apply {
                        addProperty(
                            "name",
                            "인터넷 연결이 원활하지 않습니다."
                        )
                    }.toString()
                )
            )
        }
    }

    override fun getData(type: RepositoryImpl.ParseData): Flowable<String> =
        Flowable.create(FlowableOnSubscribe<String> { emitter ->
            try {
                Jsoup.connect(urlUtil.urlList[type.ordinal])
                    .method(Connection.Method.GET)
                    .execute()
                    .apply {
                        this.parse().select(".Box-body").select("tbody").text().also {
                            emitter.onNext(it)
                        }
                    }
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())

}