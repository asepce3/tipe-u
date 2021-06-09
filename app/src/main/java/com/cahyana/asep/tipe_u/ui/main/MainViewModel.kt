package com.cahyana.asep.tipe_u.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.cahyana.asep.tipe_u.data.MainRepository
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.TransaksiDetail

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun saveTransaksi(detail: TransaksiDetail) {
        mainRepository.saveTransaksi(detail)
    }

    fun getAllTransaksi(): LiveData<PagedList<Transaksi>> = mainRepository.getAllTransaksi()

    fun getSaldo(): LiveData<Long> = mainRepository.getSaldo()

    fun getTransaksiDetail(id: Int): LiveData<TransaksiDetail> = mainRepository.getTransaksiDetail(id)
}