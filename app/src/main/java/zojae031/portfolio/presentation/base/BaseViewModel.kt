package zojae031.portfolio.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    protected val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

}