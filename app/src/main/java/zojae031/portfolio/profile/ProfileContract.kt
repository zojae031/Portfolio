package zojae031.portfolio.profile

import zojae031.portfolio.BaseContract
import zojae031.portfolio.data.dao.profile.BasicEntity

interface ProfileContract : BaseContract {
    interface View : BaseContract.View {
        fun showBasicInformation(data: BasicEntity)
    }

    interface Presenter : BaseContract.Presenter
}