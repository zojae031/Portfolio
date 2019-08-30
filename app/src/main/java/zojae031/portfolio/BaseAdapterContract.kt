package zojae031.portfolio

interface BaseAdapterContract {
    interface View {
        fun notifyAdapter()
    }

    interface Model {
        fun clearList()
    }
}