package zojae031.portfolio.main

import zojae031.portfolio.BaseContract

interface MainContract : BaseContract {
    interface View : BaseContract.View {
        fun showUserImage(url: String)
    }

    interface Presenter : BaseContract.Presenter
}