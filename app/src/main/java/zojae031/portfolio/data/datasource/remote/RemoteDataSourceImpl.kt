package zojae031.portfolio.data.datasource.remote

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup
import zojae031.portfolio.data.RepositoryImpl

class RemoteDataSourceImpl(private val urlList: List<String>) : RemoteDataSource {

    override fun getData(type: RepositoryImpl.ParseData): Flowable<String> =
        Flowable.create(FlowableOnSubscribe<String> { emitter ->
            try {
                Jsoup.connect(urlList[type.ordinal])
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
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())

    companion object {
        private var INSTANCE: RemoteDataSource? = null
        fun getInstance(urlList: List<String>): RemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = RemoteDataSourceImpl(urlList)
            }
            return INSTANCE!!
        }
    }
}