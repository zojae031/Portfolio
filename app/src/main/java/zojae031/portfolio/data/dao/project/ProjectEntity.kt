package zojae031.portfolio.data.dao.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectEntity(
    @PrimaryKey val image: String,
    val name: String,
    val prize: String,
    val text: String,
    val competition: String,
    val video: String,
    val skills: String,
    val git: String,
    val date: String
)