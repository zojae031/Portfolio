package zojae031.portfolio.profile

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.profile.BasicEntity
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

class ProfilePresenter(private val view: ProfileContract.View, private val repository: Repository) :
    ProfileContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getData(RemoteDataSourceImpl.ParseData.PROFILE)
            .map { data ->
                JsonParser().parse(data).asJsonObject.run {
                    Gson().fromJson(this, BasicEntity::class.java)
                }
            }
            .doOnSuccess { entity ->
                repository.insertData(RemoteDataSourceImpl.ParseData.PROFILE, entity)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ entity ->
                view.showBasicInformation(entity)

            }, { t ->
                view.showToast(t.message.toString())
                Log.e("ProfilePresenter", t.message)
            }
            ).also { compositeDisposable.add(it) }


    }

    override fun onPause() {
        compositeDisposable.clear()
    }
}