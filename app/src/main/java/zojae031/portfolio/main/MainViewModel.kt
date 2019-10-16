package zojae031.portfolio.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.util.DataConvertUtil

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val pageLimit = 2

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _mainEntity = MutableLiveData<MainEntity>()
    val mainEntity: LiveData<MainEntity>
        get() = _mainEntity

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.MAIN)
            .map { data ->
                DataConvertUtil.stringToMain(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _loadingState.value = false }
            .doOnSubscribe { _loadingState.value = true }
            .subscribe({ entity ->
                _mainEntity.value = entity
            }, { t ->
                _error.value = t.message
                Log.e("MainViewModel", t.message)
            }).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }


}