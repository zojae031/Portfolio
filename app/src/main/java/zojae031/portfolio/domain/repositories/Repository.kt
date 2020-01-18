package zojae031.portfolio.domain.repositories


import io.reactivex.Flowable
import io.reactivex.Single
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity

interface Repository {
    fun getUserList(): Single<List<String>>
    fun getData(type: RepositoryImpl.ParseData): Flowable<String>

    fun getProfile(): Flowable<ProfileEntity>

    fun getProjectData(): Flowable<List<ProjectEntity>>

    fun getTecData(): Flowable<List<TecEntity>>

}