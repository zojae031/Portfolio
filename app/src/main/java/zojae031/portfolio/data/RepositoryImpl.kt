package zojae031.portfolio.data

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.domain.repositories.Repository
import zojae031.portfolio.util.NetworkUtil

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val network: NetworkUtil
) : Repository {

    override fun getUserList(): Single<List<String>> =
        remoteDataSource.getUserList().subscribeOn(Schedulers.io())

    override fun parseProfile(): Flowable<ProfileEntity> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getProfile().toFlowable()
                .doOnNext { localDataSource.deleteProfile(it) }
                .mergeWith(remoteDataSource.getProfile().toFlowable().doOnNext {
                    localDataSource.insertProfile(it)
                })
        } else { 
            localDataSource.getProfile().toFlowable()
        }.subscribeOn(Schedulers.io())
    }

    override fun getData(type: ParseData): Flowable<String> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            Flowable.concat(
                localDataSource.getData(type).toFlowable()
                    .doOnNext { localDataSource.deleteData(type, it) },
                remoteDataSource.getData(type).toFlowable()
                    .doOnNext { localDataSource.insertData(type, it) }
            )
        } else {
            localDataSource.getData(type).toFlowable()
        }.subscribeOn(Schedulers.io())

    }

    enum class ParseData {
        PROFILE, PROJECT, TEC, MAIN
    }

}
