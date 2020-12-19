package id.ac.ui.cs.mobileprogramming.hira.youngsil.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey() @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val quote: String
) {
}