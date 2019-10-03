package zojae031.portfolio.util

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class UrlUtil private constructor(pref: SharedPreferences) {
    private val front = "https://github.com/"
    private val end = "/Portfolio/issues/"
    private val editor = pref.edit()
    val urlList = mutableListOf(
        "https://github.com/zojae031/Portfolio/issues/1",
        "https://github.com/zojae031/Portfolio/issues/2",
        "https://github.com/zojae031/Portfolio/issues/3",
        "https://github.com/zojae031/Portfolio/issues/4"
    )

    init {
        pref.getString("id", "zojae031")?.apply {
            Log.e("seturl", this)
            setUrl(this)
        }
    }


    fun setUrl(name: String) {
        var index = 1
        urlList.replaceAll {
            front + name + end + index++
        }

        with(editor) {
            putString("id", name)
            apply()
        }

    }

    companion object {
        private var INSTANCE: UrlUtil? = null
        fun getInstance(pref: SharedPreferences): UrlUtil {
            if (INSTANCE == null) {
                INSTANCE = UrlUtil(pref)
            }
            return INSTANCE!!
        }
    }
}
