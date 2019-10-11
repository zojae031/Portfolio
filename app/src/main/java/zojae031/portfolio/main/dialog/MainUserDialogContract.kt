package zojae031.portfolio.main.dialog

import zojae031.portfolio.BaseContract

interface MainUserDialogContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun setAdapter(
            view: MainUserDialogAdapterContract.View,
            model: MainUserDialogAdapterContract.Model
        )
    }
}