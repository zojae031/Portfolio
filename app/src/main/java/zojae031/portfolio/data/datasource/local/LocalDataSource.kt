package zojae031.portfolio.data.datasource.local

import io.reactivex.Flowable
import io.reactivex.Maybe
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity

interface LocalDataSource {
    //get
    fun getData(type: RepositoryImpl.ParseData): Maybe<String>

    fun getProfile(): Maybe<ProfileEntity>

    fun getProject(): Flowable<List<ProjectEntity>>

    fun getTec(): Flowable<List<TecEntity>>

    //insert
    fun insertData(type: RepositoryImpl.ParseData, data: String)

    fun insertProfile(data: ProfileEntity)

    fun insertProject(data: ProjectEntity)

    fun insertTec(data: TecEntity)

    //delete
    fun deleteData(type: RepositoryImpl.ParseData, data: String)

    fun deleteProfile(data: ProfileEntity)

    fun deleteProject(data: ProjectEntity)

    fun deleteTec(data: TecEntity)

}