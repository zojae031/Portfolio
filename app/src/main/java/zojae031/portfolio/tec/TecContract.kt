package zojae031.portfolio.tec

import zojae031.portfolio.base.BaseContract

interface TecContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun setAdapter(view: TecAdapterContract.View, model: TecAdapterContract.Model)
    }
}