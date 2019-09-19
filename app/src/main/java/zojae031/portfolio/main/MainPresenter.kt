package zojae031.portfolio.main

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.main.MainEntity

class MainPresenter(private val view: MainContract.View, private val repository: Repository) :
    MainContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.USER_IMAGE)
            .map { data ->
                JsonParser().parse(data).asJsonObject.run {
                    Gson().fromJson(this, MainEntity::class.java)
                }
            }.doOnSuccess { entity ->
                repository.insertData(RepositoryImpl.ParseData.USER_IMAGE, entity)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { view.hideProgress() }
            .doOnSubscribe { view.showProgress() }
            .subscribe({ entity ->
                view.showUserImage(entity.userImage)
                view.setNotice(entity.notice)
            }, { t ->
                view.showToast(t.message.toString())
            }).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }
}