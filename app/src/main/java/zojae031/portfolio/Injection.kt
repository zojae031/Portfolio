package zojae031.portfolio

import android.content.Context
import android.net.ConnectivityManager
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.DataBase
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

class Injection private constructor(val context: Context) {
    companion object {
        private var repository: Repository? = null

        fun getRepository(context: Context): Repository {
            if (repository == null) {
                repository = RepositoryImpl.getInstance(
                    LocalDataSourceImpl.getInstance(DataBase.getInstance(context)),
                    RemoteDataSourceImpl,
                    context.getSystemService(
                        Context.CONNECTIVITY_SERVICE
                    ) as ConnectivityManager
                )
            }
            return repository!!
        }


    }
}