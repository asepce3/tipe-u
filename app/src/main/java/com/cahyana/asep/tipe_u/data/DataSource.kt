package com.cahyana.asep.tipe_u.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.TransaksiDetail

interface DataSource {

    fun saveTransaksi(detail: TransaksiDetail)

    fun getAllTransaksi(): LiveData<PagedList<Transaksi>>

    fun getSaldo(): LiveData<Long>

    fun getTransaksiDetail(id: Int): LiveData<TransaksiDetail>
}