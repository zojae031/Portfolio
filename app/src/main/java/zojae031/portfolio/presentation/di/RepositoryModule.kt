package zojae031.portfolio.presentation.di

import org.koin.dsl.module
import zojae031.portfolio.domain.repositories.Repository
import zojae031.portfolio.data.RepositoryImpl

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get(), get()) }
}