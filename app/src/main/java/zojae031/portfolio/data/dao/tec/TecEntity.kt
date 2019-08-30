package zojae031.portfolio.data.dao.tec

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TecEntity(@PrimaryKey val name: String, val image: String, val source: String)