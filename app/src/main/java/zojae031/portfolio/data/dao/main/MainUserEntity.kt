package zojae031.portfolio.data.dao.main


data class MainUserEntity(val images: String?, val name: String, var listener: ((String) -> Unit)? = null)

