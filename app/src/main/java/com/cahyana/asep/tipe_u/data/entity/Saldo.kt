package com.cahyana.asep.tipe_u.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saldo")
data class Saldo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "u100k")
    var u100k: Int,

    @ColumnInfo(name = "u50k")
    var u50k: Int,

    @ColumnInfo(name = "u20k")
    var u20k: Int,

    @ColumnInfo(name = "u10k")
    var u10k: Int,

    @ColumnInfo(name = "u5k")
    var u5k: Int,

    @ColumnInfo(name = "u2k")
    var u2k: Int,

    @ColumnInfo(name = "u1k")
    var u1k: Int,

    @ColumnInfo(name = "u5")
    var u5: Int,

    @ColumnInfo(name = "u2")
    var u2: Int,

    @ColumnInfo(name = "u1")
    var u1: Int,

    @ColumnInfo(name = "saldo")
    var saldo: Long
)
