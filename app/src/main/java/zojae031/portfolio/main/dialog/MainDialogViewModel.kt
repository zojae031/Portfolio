package zojae031.portfolio.main.dialog

import android.util.Log
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

    var error = MutableLiveData<String>()
    var userList = MutableLiveData<List<MainUserEntity>>()
    var userName = MutableLiveData<String>()

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
                error.value = it.message
                Log.e("MainDialogViewModel", it.message)
            }
            .subscribe { data ->
                userList.value = data
            }.also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }

    private fun onClick(name: String) {
        urlUtil.setUrl(name.replace("@", ""))
        userName.value = name
    }
}