package zojae031.portfolio.data

import android.net.ConnectivityManager
import io.reactivex.Single
import zojae031.portfolio.data.dao.BasicEntity
import zojae031.portfolio.data.dao.CompetitionEntity
import zojae031.portfolio.data.datasource.local.LocalDataSource
import zojae031.portfolio.data.datasource.remote.RemoteDataSource

class RepositoryImpl private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val manager: ConnectivityManager
) : Repository {

    override fun getBasicInformation(): Single<String> {
        return if (manager.activeNetwork != null) {//네트워크 연결상태 on
            if (remoteDataSource.isDirty[0]) {//캐시가 지저분하면 로컬에서 땡겨옴
                localDataSource.getBasicInformation()
            } else {
                remoteDataSource.getBasicInformation()
            }
        } else {//네트워크 연결상태 off
            localDataSource.getBasicInformation()
        }
    }

    override fun insertBasicInformation(data: BasicEntity) {
        if (remoteDataSource.isDirty[0]) { //캐시가 더러울때만 저장
            localDataSource.insertBasicInformation(data)
        }
    }

    override fun insertCompetitionInformation(data: Array<CompetitionEntity>) {
        if (remoteDataSource.isDirty[1]) {
            for (list in data) {
                localDataSource.insertProjectInformation(list)
            }
        }
    }

    override fun getCompetitionInformation(): Single<String> {
        return if (manager.activeNetwork != null) {
            if (remoteDataSource.isDirty[1]) {
                localDataSource.getProjectInformation()
            } else {
                remoteDataSource.getCompetitionInformation()
            }
        } else {
            localDataSource.getProjectInformation()
        }

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