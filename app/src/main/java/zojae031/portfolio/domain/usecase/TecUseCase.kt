package zojae031.portfolio.domain.usecase

import zojae031.portfolio.domain.repositories.Repository

class TecUseCase(private val repository: Repository) {
    fun getTecData() = repository.getTecData()
}