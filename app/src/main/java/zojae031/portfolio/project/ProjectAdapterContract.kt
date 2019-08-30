package zojae031.portfolio.project

import zojae031.portfolio.BaseAdapterContract
import zojae031.portfolio.data.dao.project.CompetitionEntity

interface ProjectAdapterContract {
    interface View : BaseAdapterContract.View {
        fun updateList(lists: Array<CompetitionEntity>)
    }

    interface Model : BaseAdapterContract.Model {
    }
}