package zojae031.portfolio.util

import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

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
            BehaviorSubject.create<Boolean>()


        override fun onAvailable(network: Network) {
            connectSubject.onNext(true)
            Timber.d("onAvailable")
            super.onAvailable(network)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            connectSubject.onNext(false)
            Timber.d("onLosing")
            super.onLosing(network, maxMsToLive)
        }

        override fun onLost(network: Network) {
            connectSubject.onNext(false)
            Timber.d("onLost")
            super.onLost(network)
        }

        override fun onUnavailable() {
            connectSubject.onNext(false)
            Timber.d("onUnavailable")
            super.onUnavailable()
        }
    }
}