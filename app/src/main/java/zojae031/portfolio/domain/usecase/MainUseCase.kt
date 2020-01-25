package zojae031.portfolio.domain.usecase

import zojae031.portfolio.domain.repositories.Repository

class MainUseCase(private val repository: Repository) {
    fun getUserList() = repository.getUserList()
    fun getMainData() = repository.getMainData()
}