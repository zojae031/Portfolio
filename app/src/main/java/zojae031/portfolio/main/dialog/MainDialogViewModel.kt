package zojae031.portfolio.main.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.util.UrlUtil

class MainDialogViewModel(
    private val repository: Repository,
    private val urlUtil: UrlUtil
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _userList = MutableLiveData<List<MainUserEntity>>()
    val userList: LiveData<List<MainUserEntity>>
        get() = _userList

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName


    fun onCreate() {
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
    }

    fun onPause() {
        compositeDisposable.clear()
    }

    private fun onClick(name: String) {
        urlUtil.setUrl(name.replace("@", ""))
        _userName.value = name
    }
}