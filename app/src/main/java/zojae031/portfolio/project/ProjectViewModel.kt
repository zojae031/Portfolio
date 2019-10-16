package zojae031.portfolio.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.util.DataConvertUtil


class ProjectViewModel(private val repository: Repository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _projectEntity = MutableLiveData<Array<ProjectEntity>>()
    val projectEntity: LiveData<Array<ProjectEntity>>
        get() = _projectEntity

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROJECT)
            .map { data ->
                DataConvertUtil.stringToProjectArray(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
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

}