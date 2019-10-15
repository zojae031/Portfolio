package zojae031.portfolio.project

import zojae031.portfolio.base.BaseContract

interface ProjectContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun setAdapter(view: ProjectAdapterContract.View, model: ProjectAdapterContract.Model)
    }
}