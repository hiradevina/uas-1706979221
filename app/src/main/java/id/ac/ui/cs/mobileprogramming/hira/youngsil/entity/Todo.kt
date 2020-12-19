package id.ac.ui.cs.mobileprogramming.hira.youngsil.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey() @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "deadline") val deadline: Long,
) {
}