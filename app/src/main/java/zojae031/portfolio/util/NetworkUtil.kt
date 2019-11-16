package zojae031.portfolio.util

import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
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
            PublishSubject.create<Boolean>()


        override fun onAvailable(network: Network) {
            connectSubject.onNext(true)
            Timber.d("onAvailable $connectSubject")
            super.onAvailable(network)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            connectSubject.onNext(false)
            Timber.d("onLosing $connectSubject")
            super.onLosing(network, maxMsToLive)
        }

        override fun onLost(network: Network) {
            connectSubject.onNext(false)
            Timber.d("onLost $connectSubject")
            super.onLost(network)
        }

        override fun onUnavailable() {
            connectSubject.onNext(false)
            Timber.d("onUnavailable $connectSubject")
            super.onUnavailable()
        }
    }
}