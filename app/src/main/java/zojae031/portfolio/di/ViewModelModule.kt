package zojae031.portfolio.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zojae031.portfolio.viewmodel.MainViewModel
import zojae031.portfolio.viewmodel.ProfileViewModel
import zojae031.portfolio.viewmodel.ProjectViewModel
import zojae031.portfolio.viewmodel.TecViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ProjectViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { TecViewModel(get()) }

}