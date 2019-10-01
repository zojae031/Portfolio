package zojae031.portfolio.profile

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.util.DataConvertUtil

class ProfilePresenter(private val view: ProfileContract.View, private val repository: Repository) :
    ProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROFILE)
            .map { data ->
                DataConvertUtil.stringToProfile(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .doAfterNext { view.hideProgress() }
            .subscribe({ entity ->
                view.showBasicInformation(entity)
            }, { t ->
                view.showToast(t.message.toString())
                Log.e("ProfilePresenter", t.message)
            }
            ).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }
}