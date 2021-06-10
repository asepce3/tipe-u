package com.cahyana.asep.tipe_u.ui.debit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.tipe_u.R
import com.cahyana.asep.tipe_u.data.Resource
import com.cahyana.asep.tipe_u.data.entity.*
import com.cahyana.asep.tipe_u.databinding.ActivityDebitBinding
import com.cahyana.asep.tipe_u.ui.main.MainViewModel
import com.cahyana.asep.tipe_u.utils.DateUtils
import com.cahyana.asep.tipe_u.utils.UangUtils
import com.cahyana.asep.tipe_u.viewmodel.ViewModelFactory

class DebitActivity : AppCompatActivity(), DebitAdapter.DebitListener {
    private lateinit var activityDebitBinding: ActivityDebitBinding
    private lateinit var viewModel: MainViewModel
    private var jumlah: Long = 0
    private var items = arrayListOf<Uang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDebitBinding = ActivityDebitBinding.inflate(layoutInflater)
        setContentView(activityDebitBinding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        items.addAll(UangUtils.getUang())
        val adapter = DebitAdapter().apply {
            setItems(items)
            setDebitListener(this@DebitActivity)
        }

        activityDebitBinding.tglEdt.setText(DateUtils.getCurrentDate())

        activityDebitBinding.rvMain.layoutManager = LinearLayoutManager(this)
        activityDebitBinding.rvMain.adapter = adapter

        activityDebitBinding.btnSimpan.setOnClickListener {
            val detail = generateTransaksi()
            if (detail.transaksi.jumlah > 0) {
                viewModel.saveTransaksi(detail).observe(this, {
                    when(it.status) {
                        Resource.Status.SUCCESS -> finish()
                        Resource.Status.ERROR -> {
                            Toast.makeText(this@DebitActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                        Resource.Status.LOADING -> {}
                    }
                })
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.debit)
    }

    override fun tambah(type: UangType) {
        jumlah += type.tvalue
        activityDebitBinding.jumlahEdt.setText(jumlah.toString())
        val uang = items.find { it.type == type }
        if (uang != null) {
            uang.jumlah++
        }
    }

    override fun kurang(type: UangType) {
        if (jumlah > 0 && type.tvalue <= jumlah) {
            jumlah -= type.tvalue
            activityDebitBinding.jumlahEdt.setText(jumlah.toString())
            val uang = items.find { it.type == type }
            if (uang != null)
                uang.jumlah--
        }
    }

    private fun generateTransaksi(): TransaksiDetail {
        val tanggal = activityDebitBinding.tglEdt.text.toString().trim()
        val uang = items.filter { it.jumlah > 0 }
        val transaksi = Transaksi(jumlah = jumlah, tanggal = tanggal, type = TransaksiType.DEBIT.ordinal)
        return TransaksiDetail(transaksi, uang)
    }
}