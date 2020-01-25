package zojae031.portfolio.presentation.di

import org.koin.dsl.module
import zojae031.portfolio.domain.usecase.MainUseCase
import zojae031.portfolio.domain.usecase.ProfileUssCase
import zojae031.portfolio.domain.usecase.ProjectUseCase
import zojae031.portfolio.domain.usecase.TecUseCase

val useCaseModule = module {
    single { MainUseCase(get()) }
    single { ProfileUssCase(get()) }
    single { ProjectUseCase(get()) }
    single { TecUseCase(get()) }
}