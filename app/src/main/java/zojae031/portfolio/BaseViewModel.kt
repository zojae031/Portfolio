package zojae031.portfolio

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected open val compositeDisposable = CompositeDisposable()

    open fun onPause() {
        compositeDisposable.clear()
    }

    abstract fun onResume()
}