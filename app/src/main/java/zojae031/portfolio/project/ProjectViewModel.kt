package zojae031.portfolio.project

import android.util.Log
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
    val projectEntity = MutableLiveData<Array<ProjectEntity>>()
    val loadingState = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROJECT)
            .map { data ->
                DataConvertUtil.stringToProjectArray(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { loadingState.value = false }
            .doOnSubscribe { loadingState.value = true }
            .subscribe({ entity ->
                projectEntity.value = entity
            }, { t ->
                error.value = t.message
                Log.e("ProjectViewModel", t.localizedMessage)
            }).also { compositeDisposable.add(it) }
    }

    fun onPause() {
        compositeDisposable.clear()
    }

}