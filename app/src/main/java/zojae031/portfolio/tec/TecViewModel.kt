package zojae031.portfolio.tec

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.util.DataConvertUtil

class TecViewModel(private val repository: Repository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _tecList = MutableLiveData<Array<TecEntity>>()
    val tecList: LiveData<Array<TecEntity>>
        get() = _tecList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun onResume() {
        repository.getData(RepositoryImpl.ParseData.TEC)
            .map { data ->
                DataConvertUtil.stringToTecArray(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _loadingState.value = false }
            .doAfterNext { _loadingState.value = false }
            .doOnSubscribe { _loadingState.value = true }
            .subscribe({ data ->
                _tecList.value = data
            }, { t ->
                _error.value = t.message
                Log.e("TecViewModel", t.message)
            }).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }

}