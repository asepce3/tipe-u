package com.cahyana.asep.tipe_u.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransaksiDetail(
    @Embedded
    val transaksi: Transaksi,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_transaksi"
    )
    val uang: List<Uang>,
)