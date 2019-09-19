package zojae031.portfolio

interface BaseContract {
    interface View {
        fun showToast(text: String)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun onCreate()
        fun onResume()
        fun onPause()
    }
}