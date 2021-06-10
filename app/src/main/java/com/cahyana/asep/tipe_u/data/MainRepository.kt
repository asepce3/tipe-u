package com.cahyana.asep.tipe_u.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cahyana.asep.tipe_u.data.entity.Saldo
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.TransaksiDetail
import com.cahyana.asep.tipe_u.data.local.TransaksiDao
import com.cahyana.asep.tipe_u.exception.SaldoException
import com.cahyana.asep.tipe_u.utils.AppExecutors
import kotlin.Exception

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

    private val saveLiveData: MutableLiveData<Resource<String>> = MutableLiveData()

    override fun saveTransaksi(detail: TransaksiDetail): LiveData<Resource<String>> {
        appExecutors.diskIO().execute {
            try {
                transaksiDao.saveTransaksi(detail)
                saveLiveData.postValue(null)
            } catch (e: SaldoException) {

                e.message?.let { saveLiveData.postValue(Resource.error(e)) }
            } catch (e: Exception) {
                saveLiveData.postValue(Resource.error(e))
            }
        }
        return saveLiveData
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

    override fun getSaldoDetail(): LiveData<Saldo> {
        return transaksiDao.getSaldoDetail()
    }

    private fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
    }
}