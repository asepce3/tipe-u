package com.cahyana.asep.tipe_u.di

import android.content.Context
import com.cahyana.asep.tipe_u.data.MainRepository
import com.cahyana.asep.tipe_u.data.local.TransaksiDatabase
import com.cahyana.asep.tipe_u.utils.AppExecutors

object Injections {

    fun provideRepository(context: Context): MainRepository {
        val transaksiDatabase = TransaksiDatabase.getInstance(context)
        val transaksiDao = transaksiDatabase.transaksiDao()
        val appExecutors = AppExecutors()
        return MainRepository.getInstance(transaksiDao, appExecutors)
    }
}