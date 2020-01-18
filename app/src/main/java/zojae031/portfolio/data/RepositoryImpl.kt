package zojae031.portfolio.data

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource
import zojae031.portfolio.data.util.NetworkUtil
import zojae031.portfolio.domain.repositories.Repository

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val network: NetworkUtil
) : Repository {

    override fun getUserList(): Single<List<MainUserEntity>> =
        remoteDataSource.getUserList().subscribeOn(Schedulers.io())

    override fun getMainData(): Flowable<MainEntity> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getMainData()
                .doOnSuccess { localDataSource.deleteMain(it) }
                .mergeWith(remoteDataSource.getMain().doOnSuccess {
                    localDataSource.insertMain(it)
                }.toMaybe())
        } else {
            localDataSource.getMainData().toFlowable()
        }.subscribeOn(Schedulers.io())
    }

    override fun getProfile(): Flowable<ProfileEntity> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getProfile()
                .doOnSuccess { localDataSource.deleteProfile(it) }
                .mergeWith(remoteDataSource.getProfile().doOnSuccess {
                    localDataSource.insertProfile(it)
                }.toMaybe())
        } else {
            localDataSource.getProfile().toFlowable()
        }.subscribeOn(Schedulers.io())
    }

    override fun getProjectData(): Flowable<List<ProjectEntity>> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getProject()
                .doOnSuccess { entity ->
                    entity.forEach { localDataSource.deleteProject(it) }
                }
                .mergeWith(
                    remoteDataSource.getProject().doOnSuccess { entity ->
                        entity.forEach { localDataSource.insertProject(it) }
                    }.toMaybe()
                )
        } else {
            localDataSource.getProject().toFlowable()
        }.subscribeOn(Schedulers.io())
    }

    override fun getTecData(): Flowable<List<TecEntity>> {
        return if (network.isConnect) {//기본 네트워크 살아있니?
            localDataSource.getTec()
                .doOnSuccess { entity ->
                    entity.forEach { localDataSource.deleteTec(it) }
                }
                .mergeWith(remoteDataSource.getTec().doOnSuccess { entity ->
                    entity.forEach { localDataSource.insertTec(it) }
                }.toMaybe())
        } else {
            localDataSource.getTec().toFlowable()
        }.subscribeOn(Schedulers.io())
    }

    enum class ParseData {
        PROFILE, PROJECT, TEC, MAIN
    }

}
