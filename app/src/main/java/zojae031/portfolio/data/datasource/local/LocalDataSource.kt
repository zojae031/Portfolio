package zojae031.portfolio.data.datasource.local

import io.reactivex.Maybe
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity

interface LocalDataSource {
    //get
    fun getData(type: RepositoryImpl.ParseData): Maybe<String>

    fun getProfile(): Maybe<ProfileEntity>

    //insert
    fun insertData(type: RepositoryImpl.ParseData, data: String)

    fun insertProfile(data: ProfileEntity)

    //delete
    fun deleteData(type: RepositoryImpl.ParseData, data: String)

    fun deleteProfile(data: ProfileEntity)

}