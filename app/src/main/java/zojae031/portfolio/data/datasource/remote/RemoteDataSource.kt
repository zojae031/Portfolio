package zojae031.portfolio.data.datasource.remote

import io.reactivex.Single
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity

interface RemoteDataSource {
    fun getUserList(): Single<List<String>>

    fun getData(type: RepositoryImpl.ParseData): Single<String>
    fun getProfile(): Single<ProfileEntity>
    fun getProject(): Single<List<ProjectEntity>>
    fun getTec(): Single<List<TecEntity>>
}