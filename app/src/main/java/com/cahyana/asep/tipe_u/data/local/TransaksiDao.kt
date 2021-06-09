package com.cahyana.asep.tipe_u.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.TransaksiDetail
import com.cahyana.asep.tipe_u.data.entity.TransaksiType
import com.cahyana.asep.tipe_u.data.entity.Uang
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
        println(detail)
    }
}