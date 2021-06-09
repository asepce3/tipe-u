package com.cahyana.asep.tipe_u.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.TransaksiDetail
import com.cahyana.asep.tipe_u.data.local.TransaksiDao
import com.cahyana.asep.tipe_u.utils.AppExecutors

class MainRepository private constructor(
    val transaksiDao: TransaksiDao,
    val appExecutors: AppExecutors
): DataSource {

    companion object {
        private var instantce: MainRepository? = null

        fun getInstance(transaksiDao: TransaksiDao, appExecutors: AppExecutors): MainRepository =
            instantce ?: synchronized(this) {
                instantce ?: MainRepository(transaksiDao, appExecutors).apply {
                    instantce = this
                }
        }
    }

    override fun saveTransaksi(detail: TransaksiDetail) {
        appExecutors.diskIO().execute {
            transaksiDao.saveTransaksi(detail)
        }
    }

    override fun getAllTransaksi(): LiveData<PagedList<Transaksi>> {
        return LivePagedListBuilder(transaksiDao.getAllTransaksi(), getPagedListConfig()).build()
    }

    override fun getSaldo(): LiveData<Long> {
        return transaksiDao.getSaldo()
    }

    override fun getTransaksiDetail(id: Int): LiveData<TransaksiDetail> {
        return transaksiDao.getTransaksiDetail(id)
    }

    private fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
    }
}