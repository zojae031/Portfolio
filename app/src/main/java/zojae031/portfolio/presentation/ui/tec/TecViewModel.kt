package zojae031.portfolio.presentation.ui.tec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.domain.usecase.TecUseCase
import zojae031.portfolio.presentation.base.BaseViewModel

class TecViewModel(private val useCase: TecUseCase) :
    BaseViewModel() {

    private val _tecList = MutableLiveData<List<TecEntity>>()
    val tecList: LiveData<List<TecEntity>>
        get() = _tecList


    fun onResume() {
        useCase.getTecData()
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

    companion object {
        @JvmStatic
        val IMAGE_SIZE = 300
    }
}