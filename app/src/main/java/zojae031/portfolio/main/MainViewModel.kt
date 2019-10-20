package zojae031.portfolio.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.util.DataConvertUtil
import zojae031.portfolio.util.SingleLiveEvent
import zojae031.portfolio.util.UrlUtil
import java.util.concurrent.TimeUnit

class MainViewModel(private val repository: Repository, private val urlUtil: UrlUtil) :
    ViewModel() {

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

    private val _userList = MutableLiveData<List<MainUserEntity>>()
    val userList: LiveData<List<MainUserEntity>>
        get() = _userList

    private val _userName = SingleLiveEvent<String>()
    val userName: LiveData<String>
        get() = _userName

    fun onResume() {
        repository.getUserList()
            .map { data ->
                data.map {
                    Gson().fromJson(it, MainUserEntity::class.java)
                        .also { entity -> entity.listener = ::onClick }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = it.message
                Log.e("MainDialogViewModel", it.message)
            }
            .subscribe { data ->
                _userList.value = data
            }.also { compositeDisposable.add(it) }

        repository
            .getData(RepositoryImpl.ParseData.MAIN)
            .map { data ->
                DataConvertUtil.stringToMain(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { _loadingState.value = false }
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

    private fun onClick(name: String) {
        urlUtil.setUrl(name.replace("@", ""))
        _userName.value = name
    }

}