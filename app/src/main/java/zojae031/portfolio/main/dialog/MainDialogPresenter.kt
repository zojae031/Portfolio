package zojae031.portfolio.main.dialog

import android.util.Log
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import zojae031.portfolio.BaseContract
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.main.MainUserEntity

class MainDialogPresenter(
    private val view: BaseContract.View,
    private val repository: Repository
) : MainDialogContract.Presenter {
    private lateinit var adapterView: MainDialogAdapterContract.View
    private lateinit var adapterModel: MainDialogAdapterContract.Model
    override fun setAdapter(
        view: MainDialogAdapterContract.View,
        model: MainDialogAdapterContract.Model
    ) {
        adapterView = view
        adapterModel = model
    }

    override fun onCreate() {

        repository.getUserList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                data.map {
                    Gson().fromJson(it, MainUserEntity::class.java)
                }.toTypedArray()
            }
            .doOnError {
                view.showToast(it.message.toString())
                Log.e("MainDialogPresenter", it.message)
            }
            .subscribe { data ->
                adapterModel.clearList()
                adapterModel.updateList(data)
                adapterView.notifyAdapter()
            }
    }

    override fun onResume() {

    }

    override fun onPause() {

    }
}