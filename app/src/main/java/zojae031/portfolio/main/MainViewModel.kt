package zojae031.portfolio.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.util.DataConvertUtil

class MainViewModel(
    private val repository: Repository
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val pageLimit = 2
    val loadingState = MutableLiveData<Boolean>()
    val userImage = MutableLiveData<String>()
    val notice = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.MAIN)
            .map { data ->
                DataConvertUtil.stringToMain(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { loadingState.value = false }
            .doOnSubscribe { loadingState.value = true }
            .subscribe({ entity ->
                userImage.value = entity.userImage
                notice.value = entity.notice
            }, { t ->
                error.value = t.message
                Log.e("MainViewModel", t.message)
            }).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }


}