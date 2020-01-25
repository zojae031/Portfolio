package zojae031.portfolio.domain.usecase

import zojae031.portfolio.domain.repositories.Repository

class ProfileUssCase(private val repository: Repository) {
    fun getProfile() = repository.getProfile()
}