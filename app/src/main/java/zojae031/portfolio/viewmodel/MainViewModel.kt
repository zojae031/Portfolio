package zojae031.portfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import zojae031.portfolio.base.BaseViewModel
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.util.DataConvertUtil
import zojae031.portfolio.util.SingleLiveEvent
import zojae031.portfolio.util.UrlHelper
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository, private val urlHelper: UrlHelper) :
    BaseViewModel() {

    private val _mainEntity = MutableLiveData<MainEntity>()
    val mainEntity: LiveData<MainEntity>
        get() = _mainEntity

    private val _userList = MutableLiveData<List<MainUserEntity>>()
    val userList: LiveData<List<MainUserEntity>>
        get() = _userList

    private val _userName = SingleLiveEvent<String>()
    val userName: LiveData<String>
        get() = _userName


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
            .map { data ->
                data.map {
                    Gson().fromJson(it, MainUserEntity::class.java)
                        .also { entity -> entity.listener = ::onClick }
                }
            }
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
                    if (error is UnknownHostException) {
                        _error.value =
                            ERROR_MESSAGE
                        _userList.value = listOf(MainUserEntity(null, _error.value.toString()))
                    } else _error.value = error.message
                }
            ).also { compositeDisposable.add(it) }

    }

    fun getDataList() {
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
                Timber.tag("MainViewModel").e(t)
            }).also { compositeDisposable.add(it) }

    }

    fun onBackPressed() {
        backPressSubject.onNext(System.currentTimeMillis())
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

    private fun onClick(name: String) {
        urlHelper.setUrl(name.replace("@", ""))
        _userName.value = name
    }

    fun clearBackPressDisposable() {
        backPressDisposable.dispose()
    }


    companion object {
        const val ERROR_MESSAGE = "인터넷 연결이 원활하지 않습니다."
        const val TOAST_DURATION = 1000L
        @JvmStatic
        val PAGE_LIMIT = 2
        @JvmStatic
        val IMAGE_SIZE = 300
    }
}