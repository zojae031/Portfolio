package zojae031.portfolio.main.dialog

import zojae031.portfolio.BaseContract

interface MainDialogContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun setAdapter(
            view: MainDialogAdapterContract.View,
            model: MainDialogAdapterContract.Model
        )
    }
}