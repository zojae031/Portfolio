package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup
import zojae031.portfolio.data.RepositoryImpl

class RemoteDataSourceImpl(private val urlList: List<String>) : RemoteDataSource {

    override fun getData(type: RepositoryImpl.ParseData): Single<String> =
        Single.create(SingleOnSubscribe<String> { emitter ->
            try {
                Jsoup.connect(urlList[type.ordinal])
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
        }).subscribeOn(Schedulers.io())

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