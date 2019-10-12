package zojae031.portfolio.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.BaseViewModel
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.util.DataConvertUtil

class MainViewModel(
    private val repository: Repository
) :
    BaseViewModel() {

    override val compositeDisposable = CompositeDisposable()
    var loadingState = MutableLiveData<Boolean>()
    var userImage = MutableLiveData<String>()
    var notice = MutableLiveData<String>()
    var error = MutableLiveData<String>()

    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.MAIN)
            .map { data ->
                DataConvertUtil.stringToMain(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {loadingState.value = false }
            .doOnSubscribe { loadingState.value = true }
            .subscribe({ entity ->
                userImage.value = entity.userImage
                notice.value = entity.notice
            }, { t ->
                error.value = t.message
                Log.e("MainViewModel", t.message)
            }).also { compositeDisposable.add(it) }
    }


}