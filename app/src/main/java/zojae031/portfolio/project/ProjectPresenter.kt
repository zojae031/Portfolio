package zojae031.portfolio.project

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.util.DataConvertUtil


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
                DataConvertUtil.stringToProjectArray(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { view.hideProgress() }
            .doOnSubscribe { view.showProgress() }
            .subscribe({ entity ->
                adapterModel.clearList()
                adapterModel.updateList(entity)
                adapterView.notifyAdapter()
            }, { t ->
                view.showToast(t.message.toString())
                Log.e("ProjectPresenter", t.localizedMessage)
            }).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }

}