package zojae031.portfolio.domain.usecase

import zojae031.portfolio.domain.repositories.Repository

class ProjectUseCase(private val repository: Repository) {
    fun getProjectData() = repository.getProjectData()
}