package zojae031.portfolio.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.util.DataConvertUtil

class MainPresenter(
    private val view: MainContract.View,
    private val repository: Repository
) :
    MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.MAIN)
            .map { data ->
                DataConvertUtil.stringToMain(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { view.hideProgress() }
            .doOnSubscribe { view.showProgress() }
            .subscribe({ entity ->
                view.showUserImage(entity.userImage)
                view.setNotice(entity.notice)
            }, { t ->
                view.showToast(t.message.toString())
                Log.e("MainPresenter", t.message)
            }).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }
}