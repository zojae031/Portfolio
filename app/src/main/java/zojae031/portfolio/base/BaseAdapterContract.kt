package zojae031.portfolio.base

interface BaseAdapterContract {
    interface View {
        fun notifyAdapter()
    }

    interface Model {
        fun clearList()
    }
}