package zojae031.portfolio.project

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.project.CompetitionEntity


class ProjectPresenter(private val view: ProjectContract.View, private val repository: Repository) :
    ProjectContract.Presenter {
    private lateinit var adapterView: ProjectAdapterContract.View
    private lateinit var adapterModel: ProjectAdapterContract.Model
    private val compositeDisposable = CompositeDisposable()
    override fun setAdapter(view: ProjectAdapterContract.View, model: ProjectAdapterContract.Model) {
        adapterView = view
        adapterModel = model
    }

    override fun onCreate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        repository
            .getCompetitionData()
            .map { data ->
                JsonParser().parse(data).asJsonArray.run {
                    Gson().fromJson(this, Array<CompetitionEntity>::class.java)
                }
            }
            .doOnSuccess { repository.insertCompetitionData(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { entity ->
                adapterView.clearList()
                adapterView.updateList(entity)
                adapterModel.notifyAdapter()
            }.also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }

}