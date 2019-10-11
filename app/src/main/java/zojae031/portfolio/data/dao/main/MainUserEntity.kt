package zojae031.portfolio.data.dao.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainUserEntity(@PrimaryKey val images: String, val name: String)