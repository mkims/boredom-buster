package eu.maxkim.boredombuster.activity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Activity(
    @field:Json(name = "activity")
    val name: String,
    val type: Type,
    @field:Json(name = "participants")
    @ColumnInfo(name = "participant_count")
    val participantCount: Int,
    val price: Float,
    val link: String,
    @PrimaryKey
    val key: String,
    val accessibility: Float
) {
    enum class Type {
        Education,
        Recreational,
        Social,
        Diy,
        Charity,
        Cooking,
        Relaxation,
        Music,
        Busywork
    }
}

