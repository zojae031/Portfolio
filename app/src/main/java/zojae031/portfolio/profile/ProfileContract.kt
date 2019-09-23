package zojae031.portfolio.profile

import zojae031.portfolio.BaseContract
import zojae031.portfolio.data.dao.profile.ProfileEntity

interface ProfileContract : BaseContract {
    interface View : BaseContract.View {
        fun showBasicInformation(data: ProfileEntity)
    }

    interface Presenter : BaseContract.Presenter
}