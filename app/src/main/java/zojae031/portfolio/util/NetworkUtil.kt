package zojae031.portfolio.util

import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class NetworkUtil(
    private val manager: ConnectivityManager
) {
    private val callback = NetworkCallback()
    var isConnect: Boolean = false

    fun checkNetworkInfo(): Observable<Boolean>? {
        manager.registerDefaultNetworkCallback(callback)
        return callback.connectSubject.subscribeOn(Schedulers.io()).doOnNext { isConnect = it }
    }

    fun destroyNetwork() {
        manager.unregisterNetworkCallback(callback)
    }


    private class NetworkCallback :
        ConnectivityManager.NetworkCallback() {
        val connectSubject =
            PublishSubject.create<Boolean>()


        override fun onAvailable(network: Network) {
            connectSubject.onNext(true)
            Log.e("onAvailable", connectSubject.toString())
            super.onAvailable(network)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            connectSubject.onNext(false)
            Log.e("onLosing", connectSubject.toString())
            super.onLosing(network, maxMsToLive)
        }

        override fun onLost(network: Network) {
            connectSubject.onNext(false)
            Log.e("onLost", connectSubject.toString())
            super.onLost(network)
        }

        override fun onUnavailable() {
            connectSubject.onNext(false)
            Log.e("onUnavailable", connectSubject.toString())
            super.onUnavailable()
        }
    }
}