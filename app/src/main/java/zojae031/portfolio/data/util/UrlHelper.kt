package zojae031.portfolio.data.util

import android.content.SharedPreferences

class UrlHelper(pref: SharedPreferences) {

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

        urlList.replaceAll {
            front + name + end + session[index++]
        }

        with(editor) {
            putString("id", name)
            apply()
        }

    }

    fun getUserListUrl() =
        BASIC_URL

    companion object {
        private const val BASIC_URL = "https://github.com/zojae031/Portfolio/network/members"
        private const val front = "https://github.com/"
        private const val end = "/Portfolio/blob/json/"
        private val session = listOf("BasicData", "ProjectData", "TecData", "MainData")
    }
}
