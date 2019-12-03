package zojae031.portfolio.di

import org.koin.dsl.module
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get(), get()) }
}