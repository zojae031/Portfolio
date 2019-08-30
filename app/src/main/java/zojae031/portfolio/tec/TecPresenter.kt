package zojae031.portfolio.tec

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import zojae031.portfolio.data.Repository

class TecPresenter(private val view: TecContract.View, private val repository: Repository) :
    TecContract.Presenter {


    private lateinit var adapterView: TecAdapterContract.View
    private lateinit var adapterModel: TecAdapterContract.Model

    override fun setAdapter(view: TecAdapterContract.View, model: TecAdapterContract.Model) {
        adapterView = view
        adapterModel = model
    }

    override fun onCreate() {

    }

    override fun onResume() {
        repository.getTecData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                Log.e("result ", data)
            }

    }

    override fun onPause() {

    }

}