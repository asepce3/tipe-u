package com.cahyana.asep.tipe_u.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.Uang

@Database(entities = [Transaksi::class, Uang::class], version = 1, exportSchema = false)
abstract class TransaksiDatabase : RoomDatabase() {

    abstract fun transaksiDao(): TransaksiDao

    companion object {
        private var instance: TransaksiDatabase? = null

        fun getInstance(context: Context): TransaksiDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TransaksiDatabase::class.java,
                    "transaksi004.db"
                ).build().apply {
                    instance = this
                }
            }
    }
}