package zojae031.portfolio.data

import android.net.ConnectivityManager
import io.reactivex.Single
import zojae031.portfolio.data.dao.profile.BasicEntity
import zojae031.portfolio.data.dao.project.CompetitionEntity
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val manager: ConnectivityManager
) : Repository {

    override fun getBasicData(): Single<String> {
        return if (manager.activeNetwork != null) {//네트워크 연결상태 on
            if (remoteDataSource.isDirty[0]) {//캐시가 지저분하면 로컬에서 땡겨옴
                localDataSource.getBasicData()
            } else {
                remoteDataSource.getBasicData()
            }
        } else {//네트워크 연결상태 off
            localDataSource.getBasicData()
        }
    }

    override fun insertBasicData(data: BasicEntity) {
        if (remoteDataSource.isDirty[0]) { //캐시가 더러울때만 저장
            localDataSource.insertBasicData(data)
        }
    }

    override fun getCompetitionData(): Single<String> {
        return if (manager.activeNetwork != null) {
            if (remoteDataSource.isDirty[1]) {
                localDataSource.getProjectData()
            } else {
                remoteDataSource.getCompetitionData()
            }
        } else {
            localDataSource.getProjectData()
        }

    }
    override fun insertCompetitionData(data: Array<CompetitionEntity>) {
        if (remoteDataSource.isDirty[1]) {
            for (list in data) {
                localDataSource.insertProjectData(list)
            }
        }
    }

    override fun getTecData(): Single<String> {
        return remoteDataSource.getTecData()
    }

    companion object {
        private var INSTANCE: RepositoryImpl? = null
        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource,
            manager: ConnectivityManager
        ): RepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = RepositoryImpl(localDataSource, remoteDataSource, manager)
            }
            return INSTANCE!!
        }
    }
}