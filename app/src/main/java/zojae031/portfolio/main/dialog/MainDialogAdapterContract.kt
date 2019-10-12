package zojae031.portfolio.main.dialog

import zojae031.portfolio.BaseAdapterContract
import zojae031.portfolio.data.dao.main.MainUserEntity

interface MainDialogAdapterContract {
    interface View : BaseAdapterContract.View

    interface Model : BaseAdapterContract.Model {
        fun updateList(lists: Array<MainUserEntity>)
    }
}