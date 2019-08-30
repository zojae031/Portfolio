package zojae031.portfolio.tec

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.tec.TecEntity

class TecPresenter(private val view: TecContract.View, private val repository: Repository) :
    TecContract.Presenter {


    private lateinit var adapterView: TecAdapterContract.View
    private lateinit var adapterModel: TecAdapterContract.Model
    private val compositeDisposable = CompositeDisposable()

    override fun setAdapter(view: TecAdapterContract.View, model: TecAdapterContract.Model) {
        adapterView = view
        adapterModel = model
    }

    override fun onCreate() {

    }

    override fun onResume() {
        repository.getTecData()
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                JsonParser().parse(data).asJsonArray.run {
                    Gson().fromJson(this, Array<TecEntity>::class.java)
                }
            }
            .subscribe { data ->
                adapterModel.clearList()
                adapterModel.updateList(data)
                adapterView.notifyAdapter()
            }.also { compositeDisposable.add(it) }

    }

    override fun onPause() {
        compositeDisposable.clear()
    }

}