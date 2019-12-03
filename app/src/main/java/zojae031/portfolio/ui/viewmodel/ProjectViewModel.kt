package zojae031.portfolio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import zojae031.portfolio.base.BaseViewModel
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.project.ProjectEntityOnListener
import zojae031.portfolio.util.DataConvertUtil


class ProjectViewModel(private val repository: Repository) :
    BaseViewModel() {

    private val _projectEntity = MutableLiveData<List<ProjectEntityOnListener>>()
    val projectEntity: LiveData<List<ProjectEntityOnListener>>
        get() = _projectEntity

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
                Timber.tag("ProjectViewModel").e(t.localizedMessage)
            }).also { compositeDisposable.add(it) }
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

    private fun onClick(data: ProjectEntityOnListener) {
        _listData.value = data
    }

    companion object {
        @JvmStatic
        val IMAGE_SIZE = 300
    }
}