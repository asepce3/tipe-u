package com.cahyana.asep.tipe_u.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.cahyana.asep.tipe_u.data.entity.*
import com.cahyana.asep.tipe_u.exception.SaldoException

@Dao
abstract class TransaksiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTransaksi(transaksi: Transaksi): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveUang(uang: List<Uang>)

    @Transaction
    @Query("SELECT * FROM transaksi WHERE id=:id")
    abstract fun getTransaksiDetail(id: Int): LiveData<TransaksiDetail>

    @Query("SELECT saldo FROM transaksi WHERE id=(SELECT MAX(id) FROM transaksi)")
    abstract fun _getSaldo(): Long

    @Query("SELECT saldo FROM transaksi WHERE id=(SELECT MAX(id) FROM transaksi)")
    abstract fun getSaldo(): LiveData<Long>

    @Query("SELECT * FROM transaksi ORDER BY id DESC")
    abstract fun getAllTransaksi(): DataSource.Factory<Int, Transaksi>

    @Query("SELECT * FROM saldo")
    abstract fun getDataSaldo(): Saldo

    @Query("SELECT * FROM saldo")
    abstract fun getSaldoDetail(): LiveData<Saldo>

    @Update
    abstract fun updateDataSaldo(saldo: Saldo)

    @Transaction
    open fun saveTransaksi(detail: TransaksiDetail) {

        val oldSaldo = _getSaldo()
        var newSaldo: Long = 0
        when(detail.transaksi.type) {
            TransaksiType.DEBIT.ordinal -> {
                if (oldSaldo >= detail.transaksi.jumlah) {
                    newSaldo = oldSaldo - detail.transaksi.jumlah
                } else {
                    throw SaldoException("Saldo tidak cukup")
                }
            }
            TransaksiType.KREDIT.ordinal -> newSaldo = oldSaldo + detail.transaksi.jumlah
        }
        detail.transaksi.saldo = newSaldo
        val idTransaksi = saveTransaksi(detail.transaksi)
        detail.uang.map { it.idTransaksi = idTransaksi.toInt() }
        saveUang(detail.uang)

        updateDataSaldo(newSaldo, detail)

    }

    private fun updateDataSaldo(
        newSaldo: Long,
        detail: TransaksiDetail
    ) {
        val dataSaldo = getDataSaldo()
        dataSaldo.saldo = newSaldo
        when (detail.transaksi.type) {
            TransaksiType.KREDIT.ordinal -> {
                tambahDataSaldo(dataSaldo, detail)
            }
            TransaksiType.DEBIT.ordinal -> {
                kurangDataSaldo(dataSaldo, detail)
            }
        }
    }

    private fun kurangDataSaldo(
        dataSaldo: Saldo,
        detail: TransaksiDetail
    ) {
        dataSaldo.apply {
            detail.uang.forEach {
                when (it.type) {
                    UangType.T_100K -> {
                        if (u100k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_100K.tname} tidak cukup")
                        u100k -= it.jumlah
                    }
                    UangType.T_50K -> {
                        if (u50k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_50K.tname} tidak cukup")
                        u50k -= it.jumlah
                    }
                    UangType.T_20K -> {
                        if (u20k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_20K.tname} tidak cukup")
                        u20k -= it.jumlah
                    }
                    UangType.T_10K -> {
                        if (u10k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_10K.tname} tidak cukup")
                        u10k -= it.jumlah
                    }
                    UangType.T_5K -> {
                        if (u5k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_5K.tname} tidak cukup")
                        u5k -= it.jumlah
                    }
                    UangType.T_2K -> {
                        if (u2k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_2K.tname} tidak cukup")
                        u2k -= it.jumlah
                    }
                    UangType.T_1K -> {
                        if (u1k < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_1K.tname} tidak cukup")
                        u1k -= it.jumlah
                    }
                    UangType.T_500 -> {
                        if (u5 < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_500.tname} tidak cukup")
                        u5 -= it.jumlah
                    }
                    UangType.T_200 -> {
                        if (u2 < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_200.tname} tidak cukup")
                        u2 -= it.jumlah
                    }
                    UangType.T_100 -> {
                        if (u1 < it.jumlah)
                            throw SaldoException("Uang pecahan ${UangType.T_100.tname} tidak cukup")
                        u1 -= it.jumlah
                    }
                }
            }
        }
        updateDataSaldo(dataSaldo)
    }

    private fun tambahDataSaldo(
        dataSaldo: Saldo,
        detail: TransaksiDetail
    ) {
        dataSaldo.apply {
            detail.uang.forEach {
                when (it.type) {
                    UangType.T_100K -> u100k += it.jumlah
                    UangType.T_50K -> u50k += it.jumlah
                    UangType.T_20K -> u20k += it.jumlah
                    UangType.T_10K -> u10k += it.jumlah
                    UangType.T_5K -> u5k += it.jumlah
                    UangType.T_2K -> u2k += it.jumlah
                    UangType.T_1K -> u1k += it.jumlah
                    UangType.T_500 -> u5 += it.jumlah
                    UangType.T_200 -> u2 += it.jumlah
                    UangType.T_100 -> u1 += it.jumlah
                }
            }
        }
        updateDataSaldo(dataSaldo)
    }
}
