package com.cahyana.asep.tipe_u.utils

import com.cahyana.asep.tipe_u.data.entity.Uang
import com.cahyana.asep.tipe_u.data.entity.UangType

object UangUtils {

    fun getUang(): List<Uang> {
        val items = arrayListOf<Uang>()
        items.add(Uang(id = 1, type = UangType.T_100K, jumlah = 0))
        items.add(Uang(id = 2, type = UangType.T_50K, jumlah = 0))
        items.add(Uang(id = 3, type = UangType.T_20K, jumlah = 0))
        items.add(Uang(id = 4, type = UangType.T_10K, jumlah = 0))
        items.add(Uang(id = 5, type = UangType.T_5K, jumlah = 0))
        items.add(Uang(id = 6, type = UangType.T_2K, jumlah = 0))
        items.add(Uang(id = 7, type = UangType.T_1K, jumlah = 0))
        items.add(Uang(id = 8, type = UangType.T_500, jumlah = 0))
        items.add(Uang(id = 9, type = UangType.T_200, jumlah = 0))
        items.add(Uang(id = 10, type = UangType.T_100, jumlah = 0))

        return items
    }
}
