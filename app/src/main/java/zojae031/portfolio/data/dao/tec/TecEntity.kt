package zojae031.portfolio.data.dao.tec

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.JsonArray
import zojae031.portfolio.data.util.JsonArrayConverter
import java.io.Serializable

@Entity
@TypeConverters(JsonArrayConverter::class)
data class TecEntity(@PrimaryKey val name: String, val image: String, val source: JsonArray) :
    Serializable
