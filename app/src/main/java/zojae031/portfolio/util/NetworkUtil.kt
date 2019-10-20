package zojae031.portfolio.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import zojae031.portfolio.main.MainActivity

class NetworkUtil(
    private val manager: ConnectivityManager,
    private val applicationContext: Context
) {

    var isConnect = false


    fun checkNetworkInfo() {
        manager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnect = true
                applicationContext.startActivity(
                    Intent(
                        applicationContext,
                        MainActivity::class.java
                    )
                )
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