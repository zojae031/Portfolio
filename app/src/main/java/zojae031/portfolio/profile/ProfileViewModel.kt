package zojae031.portfolio.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import zojae031.portfolio.base.BaseViewModel
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.util.DataConvertUtil

class ProfileViewModel(private val repository: Repository) :
    BaseViewModel() {

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
                Timber.tag("ProfileViewModel").e(t)
            }
            ).also { compositeDisposable.add(it) }
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }


    fun buttonClicked(data: String) {
        _buttonEvent.value = data
    }
}