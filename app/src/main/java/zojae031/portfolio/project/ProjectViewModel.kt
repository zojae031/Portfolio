package zojae031.portfolio.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.project.ProjectEntityOnListener
import zojae031.portfolio.util.DataConvertUtil


class ProjectViewModel(private val repository: Repository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _projectEntity = MutableLiveData<List<ProjectEntityOnListener>>()
    val projectEntity: LiveData<List<ProjectEntityOnListener>>
        get() = _projectEntity

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _listData = MutableLiveData<ProjectEntityOnListener>()
    val listData: LiveData<ProjectEntityOnListener>
        get() = _listData

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROJECT)
            .map { data ->
                DataConvertUtil.stringToProjectOnListenerList(data)
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
            .subscribe({ entity ->
                _projectEntity.value = entity
            }, { t ->
                _error.value = t.message
                Log.e("ProjectViewModel", t.localizedMessage)
            }).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }

    private fun onClick(data: ProjectEntityOnListener) {
        _listData.value = data
    }

}