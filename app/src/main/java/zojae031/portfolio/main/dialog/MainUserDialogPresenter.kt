package zojae031.portfolio.main.dialog

import android.util.Log
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import zojae031.portfolio.BaseContract
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.main.MainUserEntity

class MainUserDialogPresenter(
    private val view: BaseContract.View,
    private val repository: Repository
) : MainUserDialogContract.Presenter {
    private lateinit var adapterView: MainUserDialogAdapterContract.View
    private lateinit var adapterModel: MainUserDialogAdapterContract.Model
    override fun setAdapter(
        view: MainUserDialogAdapterContract.View,
        model: MainUserDialogAdapterContract.Model
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