package zojae031.portfolio.main.dialog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.BaseViewModel
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.main.MainUserEntity

class MainDialogViewModel(
    private val repository: Repository
) : BaseViewModel() {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var error = MutableLiveData<String>()
    var userList = MutableLiveData<Array<MainUserEntity>>()

    override fun onCreate() {

        repository.getUserList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                data.map {
                    Gson().fromJson(it, MainUserEntity::class.java)
                }.toTypedArray()
            }
            .doOnError {
                error.value = it.message
                Log.e("MainDialogViewModel", it.message)
            }
            .subscribe { data ->
                userList.value = data
            }.also { compositeDisposable.add(it) }
    }

    override fun onResume() {

    }
}