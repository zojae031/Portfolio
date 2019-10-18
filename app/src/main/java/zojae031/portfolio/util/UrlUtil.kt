package zojae031.portfolio.util

import android.content.SharedPreferences
import android.os.Build

class UrlUtil(pref: SharedPreferences) {
    private val front = "https://github.com/"
    private val end = "/Portfolio/blob/json/"
    private val session = listOf("BasicData", "ProjectData", "TecData", "MainData")
    private val editor = pref.edit()
    val urlList = mutableListOf(
        "https://github.com/zojae031/Portfolio/blob/json/BasicData",
        "https://github.com/zojae031/Portfolio/blob/json/ProjectData",
        "https://github.com/zojae031/Portfolio/blob/json/TecData",
        "https://github.com/zojae031/Portfolio/blob/json/MainData"
    )

    init {
        pref.getString("id", "zojae031")?.apply {
            setUrl(this)
        }
    }


    fun setUrl(name: String) {
        var index = 0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            for (i in 0 until urlList.size) {
                urlList.removeAt(0)
                urlList.add(front + name + end + session[index++])
            }
        } else {
            urlList.replaceAll {
                front + name + end + session[index++]
            }

        }

        with(editor) {
            putString("id", name)
            apply()
        }

    }

    fun getUserListUrl() = BASIC_URL

    companion object {
        private const val BASIC_URL = "https://github.com/zojae031/Portfolio/network/members"
    }
}
