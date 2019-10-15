package zojae031.portfolio.profile

import android.util.Log
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
    val error = MutableLiveData<String>()
    val loadingState = MutableLiveData<Boolean>()
    val profileEntity = MutableLiveData<ProfileEntity>()
    val buttonEvent = MutableLiveData<String>()

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROFILE)
            .map { data ->
                DataConvertUtil.stringToProfile(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { loadingState.value = false }
            .doOnSubscribe { loadingState.value = true }
            .subscribe({ entity ->
                profileEntity.value = entity
            }, { t ->
                error.value = t.message.toString()
                Log.e("ProfileViewModel", t.message)
            }
            ).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }

    fun buttonClicked(data: String) {
        buttonEvent.value = data
    }
}