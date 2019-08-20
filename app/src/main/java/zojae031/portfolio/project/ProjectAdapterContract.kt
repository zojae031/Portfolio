package zojae031.portfolio.project

import zojae031.portfolio.data.dao.project.CompetitionEntity

interface ProjectAdapterContract {
    interface View {
        fun clearList()
        fun updateList(lists: Array<CompetitionEntity>)
    }

    interface Model {
        fun notifyAdapter()
    }
}