package zojae031.portfolio

import android.content.Context
import android.net.ConnectivityManager
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

class Injection private constructor(private val context: Context) {
    companion object {
        private var INSTANCE: Injection? = null
        private var repository: RepositoryImpl? = null

        private fun getInstance(context: Context): Injection {
            if (INSTANCE == null) {
                INSTANCE = Injection(context)
            }
            return INSTANCE!!
        }

        fun getRepository(context: Context): RepositoryImpl {
            getInstance(context)
            if (repository == null) {
                repository = RepositoryImpl.getInstance(
                    LocalDataSourceImpl.getInstance(context),
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