package zojae031.portfolio.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.data.util.UrlHelper
import zojae031.portfolio.domain.repositories.Repository
import zojae031.portfolio.presentation.base.BaseViewModel

class MainViewModel(private val repository: Repository, private val urlHelper: UrlHelper) :
    BaseViewModel() {

    private val _mainEntity = MutableLiveData<MainEntity>()
    val mainEntity: LiveData<MainEntity>
        get() = _mainEntity

    private val _userList = MutableLiveData<List<MainUserEntity>>()
    val userList: LiveData<List<MainUserEntity>>
        get() = _userList

    private val _finishState = MutableLiveData<Boolean>()
    val finishState: LiveData<Boolean>
        get() = _finishState

    private val backPressSubject =
        BehaviorSubject.createDefault(0L)

    private val backPressDisposable = backPressSubject
        .toFlowable(BackpressureStrategy.BUFFER)
        .observeOn(AndroidSchedulers.mainThread())
        .buffer(2, 1)
        .map { it[0] to it[1] }
        .subscribe(
            {
                _finishState.value = it.second - it.first < TOAST_DURATION
            },
            {
                _error.value = it.message
            }
        )

    fun getUserList() {
        repository.getUserList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _error.value = it.message
                Timber.tag("MainDialogViewModel").e(it)
            }
            .subscribe(
                { data ->
                    _userList.value = data
                },
                { error ->
                    _error.value = error.message
                }
            ).also { compositeDisposable.add(it) }

    }

    fun getDataList() {
        repository
            .getMainData()
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { _loadingState.value = false }
            .doOnComplete { _loadingState.value = false }
            .doOnSubscribe { _loadingState.value = true }
            .subscribe({ entity ->
                _mainEntity.value = entity
            }, { t ->
                _error.value = t.message
                Timber.tag("MainViewModel").e(t)
            }).also { compositeDisposable.add(it) }

    }

    fun onBackPressed() {
        backPressSubject.onNext(System.currentTimeMillis())
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }


    fun setUrl(name: String) {
        urlHelper.setUrl(name.replace("@", ""))
    }


    fun clearBackPressDisposable() {
        backPressDisposable.dispose()
    }


    companion object {
        const val TOAST_DURATION = 1000L
        @JvmStatic
        val PAGE_LIMIT = 2
        @JvmStatic
        val IMAGE_SIZE = 300
    }
}