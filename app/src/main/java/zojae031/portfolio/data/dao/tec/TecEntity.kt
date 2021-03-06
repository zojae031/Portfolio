package zojae031.portfolio.data.dao.tec

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TecEntity(@PrimaryKey val name: String, val image: String, val source: String) :
    Parcelable