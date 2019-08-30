package zojae031.portfolio.tec

import zojae031.portfolio.BaseContract

interface TecContract {
    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter {
        fun setAdapater(view: TecAdapterContract.View, model: TecAdapterContract.Model)
    }
}