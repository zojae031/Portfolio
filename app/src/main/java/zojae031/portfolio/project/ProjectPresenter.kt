package zojae031.portfolio.project

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.project.CompetitionEntity


class ProjectPresenter(private val view: ProjectContract.View, private val repository: Repository) :
    ProjectContract.Presenter {
    private lateinit var adapterView: ProjectAdapterContract.View
    private lateinit var adapterModel: ProjectAdapterContract.Model
    private val compositeDisposable = CompositeDisposable()
    override fun setAdapter(
        view: ProjectAdapterContract.View,
        model: ProjectAdapterContract.Model
    ) {
        adapterView = view
        adapterModel = model
    }

    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.PROJECT)
            .map { data ->
                JsonParser().parse(data).asJsonArray.run {
                    Gson().fromJson(this, Array<CompetitionEntity>::class.java)
                }
            }
            .doOnSuccess { repository.insertData(RepositoryImpl.ParseData.PROJECT, it) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .doOnSuccess { view.hideProgress() }
            .subscribe({ entity ->
                adapterModel.clearList()
                adapterModel.updateList(entity)
                adapterView.notifyAdapter()

            }, { t ->
                view.showToast(t.message.toString())
                Log.e("ProjectPresenter", t.message)
            }).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }

}