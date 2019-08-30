package zojae031.portfolio.tec

import zojae031.portfolio.BaseAdapterContract

interface TecAdapterContract {
    interface View : BaseAdapterContract.View {
        fun updateList()
    }

    interface Model : BaseAdapterContract.Model
}