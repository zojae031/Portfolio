package zojae031.portfolio.domain.repositories


import io.reactivex.Flowable
import io.reactivex.Single
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.dao.profile.ProfileEntity

interface Repository {
    fun getUserList(): Single<List<String>>
    fun getData(type: RepositoryImpl.ParseData): Flowable<String>

    fun parseProfile(): Flowable<ProfileEntity>

}