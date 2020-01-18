package zojae031.portfolio.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zojae031.portfolio.ui.main.MainViewModel
import zojae031.portfolio.ui.profile.ProfileViewModel
import zojae031.portfolio.ui.project.ProjectViewModel
import zojae031.portfolio.ui.tec.TecViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ProjectViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { TecViewModel(get()) }

}