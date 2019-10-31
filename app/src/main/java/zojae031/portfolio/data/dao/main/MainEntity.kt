package zojae031.portfolio.data.dao.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainEntity(@PrimaryKey val userImage: String, val notice: String)