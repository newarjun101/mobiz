package com.kbz.mobiz.domain.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("recent")
data class RecentVo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("title")
    val title : String,
    @ColumnInfo("time_stemp")
    val timeStemp: LocalDateTime = LocalDateTime.now(),
)