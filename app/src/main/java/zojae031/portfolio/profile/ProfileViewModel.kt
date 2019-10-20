package zojae031.portfolio.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.util.DataConvertUtil

class ProfileViewModel(private val repository: Repository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _profileEntity = MutableLiveData<ProfileEntity>()
    val profileEntity: LiveData<ProfileEntity>
        get() = _profileEntity

    private val _buttonEvent = MutableLiveData<String>()
    val buttonEvent: LiveData<String>
        get() = _buttonEvent

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROFILE)
            .map { data ->
                DataConvertUtil.stringToProfile(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { _loadingState.value = false }
            .doOnComplete { _loadingState.value = false }
            .doOnSubscribe { _loadingState.value = true }
            .subscribe({ entity ->
                _profileEntity.value = entity
            }, { t ->
                _error.value = t.message.toString()
                Log.e("ProfileViewModel", t.message)
            }
            ).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }

    fun buttonClicked(data: String) {
        _buttonEvent.value = data
    }
}