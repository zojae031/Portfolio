package zojae031.portfolio.presentation.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.domain.usecase.ProjectUseCase
import zojae031.portfolio.presentation.base.BaseViewModel


class ProjectViewModel(private val useCase: ProjectUseCase) :
    BaseViewModel() {

    private val _projectEntity = MutableLiveData<List<ProjectEntity>>()
    val projectEntity: LiveData<List<ProjectEntity>>
        get() = _projectEntity

    fun onResume() {
        useCase
            .getProjectData()
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

    companion object {
        @JvmStatic
        val IMAGE_SIZE = 300
    }
}