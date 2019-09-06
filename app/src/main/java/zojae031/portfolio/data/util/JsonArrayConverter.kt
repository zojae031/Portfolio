package zojae031.portfolio.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray

class JsonArrayConverter {
    @TypeConverter
    fun fromJsonArray(data: String?): JsonArray? {
        return if (data == null) null
        else Gson().fromJson(data, JsonArray::class.java)
    }

    @TypeConverter
    fun toJsonArray(data: JsonArray?): String? {
        return if (data == null) null
        else Gson().toJson(data)
    }
}