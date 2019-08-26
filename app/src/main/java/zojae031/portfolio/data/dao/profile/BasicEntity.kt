package zojae031.portfolio.data.dao.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BasicEntity(
    @PrimaryKey val name: String,
    val age: String,
    val university: String,
    val major: String,
    val military: String,
    val hobby: String,
    val additional: String
)