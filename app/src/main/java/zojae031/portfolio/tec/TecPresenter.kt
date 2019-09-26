package zojae031.portfolio.tec

import android.util.Log
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
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
        repository.getData(RepositoryImpl.ParseData.TEC)
            .map { data ->
                JsonParser().parse(data).asJsonArray.run {
                    this.map {
                        return@map it.asJsonObject.run {
                            TecEntity(
                                get("name").asString,
                                get("image").asString,
                                get("source").toString()
                            )
                        }
                    }.toTypedArray()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.hideProgress() }
            .doOnSubscribe { view.showProgress() }
            .subscribe({ data ->
                adapterModel.clearList()
                adapterModel.updateList(data)
                adapterView.notifyAdapter()
            }, { t ->
                view.showToast(t.message.toString())
                Log.e("TecPresenter", t.message)
            }).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }

}