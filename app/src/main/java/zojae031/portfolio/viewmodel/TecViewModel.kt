package zojae031.portfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import zojae031.portfolio.base.BaseViewModel
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.tec.TecEntityOnListener
import zojae031.portfolio.util.DataConvertUtil

class TecViewModel(private val repository: Repository) :
    BaseViewModel() {

    private val _tecList = MutableLiveData<List<TecEntityOnListener>>()
    val tecList: LiveData<List<TecEntityOnListener>>
        get() = _tecList

    private val _listData = MutableLiveData<TecEntityOnListener>()
    val listData: LiveData<TecEntityOnListener>
        get() = _listData

    fun onResume() {
        repository.getData(RepositoryImpl.ParseData.TEC)
            .map { data ->
                DataConvertUtil.stringToTecOnListenerList(data)
                    .also {
                        it.map { entity ->
                            entity.listener = ::onClick
                        }
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _loadingState.value = false }
            .doAfterNext { _loadingState.value = false }
            .doOnSubscribe { _loadingState.value = true }
            .subscribe({ data ->
                _tecList.value = data
            }, { t ->
                _error.value = t.message
                Timber.tag("TecViewModel").e(t)
            }).also { compositeDisposable.add(it) }
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

    private fun onClick(data: TecEntityOnListener) {
        _listData.value = data
    }

    companion object {
        @JvmStatic
        val IMAGE_SIZE = 300
    }
}