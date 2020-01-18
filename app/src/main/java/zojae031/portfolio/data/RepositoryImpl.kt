package zojae031.portfolio.data

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity
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

    override fun getProfile(): Flowable<ProfileEntity> {
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

    override fun getProjectData(): Flowable<List<ProjectEntity>> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getProject()
                .doOnNext { entity ->
                    entity.map { localDataSource.deleteProject(it) }
                }
                .mergeWith(
                    remoteDataSource.getProject().toFlowable().doOnNext { entity ->
                        entity.map { localDataSource.insertProject(it) }
                    }
                )
        } else {
            localDataSource.getProject()
        }.subscribeOn(Schedulers.io())
    }

    override fun getTecData(): Flowable<List<TecEntity>> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getTec()
                .doOnNext { entity ->
                    entity.map { localDataSource.deleteTec(it) }
                }
                .mergeWith(remoteDataSource.getTec().toFlowable().doOnNext { entity ->
                    entity.map { localDataSource.insertTec(it) }
                })
        } else {
            localDataSource.getTec()
        }.subscribeOn(Schedulers.io())
    }

    override fun getMainData(): Flowable<MainEntity> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getMainData().toFlowable()
                .doOnNext { localDataSource.deleteMain(it) }
                .mergeWith(remoteDataSource.getMain().toFlowable().doOnNext {
                    localDataSource.insertMain(it)
                })
        } else {
            localDataSource.getMainData().toFlowable()
        }.subscribeOn(Schedulers.io())

    }

    enum class ParseData {
        PROFILE, PROJECT, TEC, MAIN
    }

}
