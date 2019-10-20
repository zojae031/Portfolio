package zojae031.portfolio.data.dao.tec

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonArray

@Entity
//@TypeConverters(JsonArrayConverter::class)
data class TecEntity(@PrimaryKey val name: String, val image: String, val source: String) :
    Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(image)
        writeString(source)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TecEntity> = object : Parcelable.Creator<TecEntity> {
            override fun createFromParcel(source: Parcel): TecEntity = TecEntity(source)
            override fun newArray(size: Int): Array<TecEntity?> = arrayOfNulls(size)
        }
    }
}

data class TecEntityOnListener(
    val name: String,
    val image: String,
    val source: JsonArray,
    var listener: (TecEntityOnListener) -> Unit
)