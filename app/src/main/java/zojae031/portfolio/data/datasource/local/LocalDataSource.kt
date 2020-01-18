package zojae031.portfolio.data.datasource.local

import io.reactivex.Maybe
import zojae031.portfolio.data.dao.main.MainEntity
import zojae031.portfolio.data.dao.profile.ProfileEntity
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.data.dao.tec.TecEntity

interface LocalDataSource {
    //get
    fun getMainData(): Maybe<MainEntity>

    fun getProfile(): Maybe<ProfileEntity>

    fun getProject(): Maybe<List<ProjectEntity>>

    fun getTec(): Maybe<List<TecEntity>>

    //insert
    fun insertMain(data: MainEntity)

    fun insertProfile(data: ProfileEntity)

    fun insertProject(data: ProjectEntity)

    fun insertTec(data: TecEntity)

    //delete
    fun deleteMain(data: MainEntity)

    fun deleteProfile(data: ProfileEntity)

    fun deleteProject(data: ProjectEntity)

    fun deleteTec(data: TecEntity)

}