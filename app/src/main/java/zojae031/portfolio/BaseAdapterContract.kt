package zojae031.portfolio

interface BaseAdapterContract {
    interface View {
        fun clearList()
    }

    interface Model {
        fun notifyAdapter()
    }
}