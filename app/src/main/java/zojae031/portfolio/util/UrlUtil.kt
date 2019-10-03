package zojae031.portfolio.util

import android.os.Build
import androidx.annotation.RequiresApi

class UrlUtil private constructor() {
    private val front = "https://github.com/"
    private val end = "/Portfolio/issues/"

    val urlList = mutableListOf(
        "https://github.com/zojae031/Portfolio/issues/1",
        "https://github.com/zojae031/Portfolio/issues/2",
        "https://github.com/zojae031/Portfolio/issues/3",
        "https://github.com/zojae031/Portfolio/issues/4"
    )

    @RequiresApi(Build.VERSION_CODES.N)
    fun setUrl(name: String) {
        var index = 1
        urlList.replaceAll {
            front + name + end + index++
        }
    }

    companion object {
        private var INSTANCE: UrlUtil? = null
        fun getInstance(): UrlUtil {
            if (INSTANCE == null) {
                INSTANCE = UrlUtil()
            }
            return INSTANCE!!
        }
    }
}
