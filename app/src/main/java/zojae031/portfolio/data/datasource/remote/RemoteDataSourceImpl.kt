package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup
import zojae031.portfolio.data.RepositoryImpl

object RemoteDataSourceImpl : RemoteDataSource {
    private val urlList = listOf(
        "https://github.com/zojae031/Portfolio/issues/1",
        "https://github.com/zojae031/Portfolio/issues/2",
        "https://github.com/zojae031/Portfolio/issues/3"
    )

    @Volatile
    override var isDirty: MutableList<Boolean> = mutableListOf(false, false, false)


    override fun getData(type: RepositoryImpl.ParseData): Single<String> {
        return parseUrl(type)
    }

    private fun parseUrl(idx: RepositoryImpl.ParseData): Single<String> =
        Single.create(SingleOnSubscribe<String> {
            try {
                Jsoup.connect(urlList[idx.ordinal])
                    .method(Connection.Method.GET)
                    .execute()
                    .apply {
                        isDirty[idx.ordinal] = true
                        it.onSuccess(this.parse().select(".d-block").select("p").text())
                    }
            } catch (e: Exception) {
                it.tryOnError(e)
            }
        }).subscribeOn(Schedulers.io())


}