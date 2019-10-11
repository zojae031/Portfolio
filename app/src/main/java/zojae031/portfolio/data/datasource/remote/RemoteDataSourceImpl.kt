package zojae031.portfolio.data.datasource.remote

import android.os.Build
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.N)
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
        } catch (e: java.lang.Exception) {
            emitter.tryOnError(e)
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

    companion object {
        private var INSTANCE: RemoteDataSource? = null
        fun getInstance(urlUtil: UrlUtil): RemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = RemoteDataSourceImpl(urlUtil)
            }
            return INSTANCE!!
        }
    }
}