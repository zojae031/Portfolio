package zojae031.portfolio.tec

import android.util.Log
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.tec.TecEntity

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
            .map { data ->
                JsonParser().parse(data.trim()).asJsonObject.run {
                    TecEntity(get("name").asString, get("image").asString, getAsJsonArray("source"))
                }
            }
            .subscribe { data ->
                Log.e("result ", data.toString())
            }

    }

    override fun onPause() {

    }

}