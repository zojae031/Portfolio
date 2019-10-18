package zojae031.portfolio.util

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log

class NetworkUtil(private val manager: ConnectivityManager) {
    @Volatile
    var isConnect = true


    fun checkNetworkInfo() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            isConnect = manager.isDefaultNetworkActive
        } else {
            manager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isConnect = true
                    Log.e("onAvailable", isConnect.toString())
                    super.onAvailable(network)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    isConnect = false
                    Log.e("onLosing", isConnect.toString())
                    super.onLosing(network, maxMsToLive)
                }

                override fun onLost(network: Network) {
                    isConnect = false
                    Log.e("onLost", isConnect.toString())
                    super.onLost(network)
                }

                override fun onUnavailable() {
                    isConnect = false
                    Log.e("onUnavailable", isConnect.toString())
                    super.onUnavailable()
                }
            })
        }
    }

}