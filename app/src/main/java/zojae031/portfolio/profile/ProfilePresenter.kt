package zojae031.portfolio.profile

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.profile.BasicEntity

class ProfilePresenter(private val view: ProfileContract.View, private val repository: Repository) :
    ProfileContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getBasicData()
            .map { data ->
                JsonParser().parse(data).asJsonObject.run {
                    Gson().fromJson(this, BasicEntity::class.java)
                }
            }
            .doOnSuccess { entity ->
                repository.insertBasicData(entity)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ entity->
                view.showBasicInformation(entity)

            }, { t ->
                view.showToast(t.message.toString())
            }
            ).also { compositeDisposable.add(it) }


    }

    override fun onPause() {
        compositeDisposable.clear()
    }
}