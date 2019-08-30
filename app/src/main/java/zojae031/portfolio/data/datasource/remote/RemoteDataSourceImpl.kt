package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup

object RemoteDataSourceImpl : RemoteDataSource {
    private val urlList = listOf(
        "https://github.com/zojae031/Portfolio/issues/1",
        "https://github.com/zojae031/Portfolio/issues/2",
        "https://github.com/zojae031/Portfolio/issues/3"
    )

    @Volatile
    override var isDirty: MutableList<Boolean> = mutableListOf(false, false, false)

    override fun getBasicData(): Single<String> {
        return parseUrl(0)
    }

    override fun getCompetitionData(): Single<String> {
        return parseUrl(1)
    }

    override fun getTecData(): Single<String> {
        return parseUrl(2)
    }

    private fun parseUrl(idx: Int): Single<String> =
        Single.create(SingleOnSubscribe<String> {
            try {
                Jsoup.connect(urlList[idx])
                    .method(Connection.Method.GET)
                    .execute()
                    .apply {
                        isDirty[idx] = true
                        it.onSuccess(this.parse().select(".d-block").select("p").text())
                    }
            } catch (e: Exception) {
                it.onError(e)
            }


        }).subscribeOn(Schedulers.io())

}