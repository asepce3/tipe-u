package com.cahyana.asep.tipe_u.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Transaksi(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "jumlah")
    val jumlah: Long,

    @ColumnInfo(name = "tanggal")
    val tanggal: String,

    @ColumnInfo(name = "type")
    val type: Int = 0,

    @ColumnInfo(name = "saldo")
    var saldo: Long = 0
)
