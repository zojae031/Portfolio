package zojae031.portfolio.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    abstract fun clearDisposable()
}