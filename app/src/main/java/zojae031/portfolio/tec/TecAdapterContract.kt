package zojae031.portfolio.tec

import zojae031.portfolio.base.BaseAdapterContract
import zojae031.portfolio.data.dao.tec.TecEntity

interface TecAdapterContract {
    interface View : BaseAdapterContract.View

    interface Model : BaseAdapterContract.Model {
        fun updateList(lists: Array<TecEntity>)
    }
}