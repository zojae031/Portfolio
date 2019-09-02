package zojae031.portfolio

import android.util.Log
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var remoteDataSource: RemoteDataSource? = null
    @Before
    fun init() {
        remoteDataSource = RemoteDataSourceImpl
    }

    @Test
    fun addition_isCorrect() {
        remoteDataSource!!.getBasicData()
            .observeOn(Schedulers.io())
            .subscribe { data ->
                Log.e("asd", data)
            }

    }
}
