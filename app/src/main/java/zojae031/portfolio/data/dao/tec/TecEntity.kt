package zojae031.portfolio.data.dao.tec

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonArray

@Entity
data class TecEntity(@PrimaryKey val name: String, val image: String, val source: JsonArray)