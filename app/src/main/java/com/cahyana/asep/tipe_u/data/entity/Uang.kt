package com.cahyana.asep.tipe_u.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Transaksi::class,
    parentColumns = ["id"],
    childColumns = ["id_transaksi"],
    onDelete = ForeignKey.CASCADE
)],
primaryKeys = ["id", "id_transaksi"])
data class Uang(

    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "id_transaksi", index = true)
    var idTransaksi: Int = 0,

    @ColumnInfo(name = "type")
    val type: UangType,

    @ColumnInfo(name = "jumlah")
    var jumlah: Int
)