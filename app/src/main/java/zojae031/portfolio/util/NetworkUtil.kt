package zojae031.portfolio.util

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkUtil private constructor(private val manager: ConnectivityManager) {
    var isConnect = true
    @RequiresApi(Build.VERSION_CODES.N)
    fun checkNetworkInfo() {
        manager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnect = true
                super.onAvailable(network)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                isConnect = false
                super.onLosing(network, maxMsToLive)
            }

            override fun onLost(network: Network) {
                isConnect = false
                super.onLost(network)
            }

            override fun onUnavailable() {
                isConnect = false
                super.onUnavailable()
            }
        })
    }

    companion object {
        private var INSTANCE: NetworkUtil? = null
        fun getInstance(manager: ConnectivityManager): NetworkUtil {
            if (INSTANCE == null) {
                INSTANCE = NetworkUtil(manager)
            }
            return INSTANCE!!
        }
    }
}