package zojae031.portfolio.data.di

import org.koin.dsl.module
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl

val repositoryModule = module {
    single<Repository> { RepositoryImpl.getInstance(get(), get(), get()) }
}