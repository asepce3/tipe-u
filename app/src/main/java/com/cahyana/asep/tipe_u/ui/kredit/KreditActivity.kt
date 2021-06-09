package com.cahyana.asep.tipe_u.ui.kredit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.tipe_u.R
import com.cahyana.asep.tipe_u.data.entity.*
import com.cahyana.asep.tipe_u.databinding.ActivityKreditBinding
import com.cahyana.asep.tipe_u.ui.main.MainViewModel
import com.cahyana.asep.tipe_u.utils.DateUtils
import com.cahyana.asep.tipe_u.utils.UangUtils
import com.cahyana.asep.tipe_u.viewmodel.ViewModelFactory

class KreditActivity : AppCompatActivity(), KreditAdapter.KreditListener {
    private lateinit var activityKreditBinding: ActivityKreditBinding

    private lateinit var viewModel: MainViewModel
    private var jumlah: Long = 0
    private var items = arrayListOf<Uang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityKreditBinding = ActivityKreditBinding.inflate(layoutInflater)
        setContentView(activityKreditBinding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        items.addAll(UangUtils.getUang())
        val adapter = KreditAdapter().apply {
            setItems(items)
            setKreditListener(this@KreditActivity)
        }

        activityKreditBinding.tglEdt.setText(DateUtils.getCurrentDate())

        activityKreditBinding.rvMain.layoutManager = LinearLayoutManager(this)
        activityKreditBinding.rvMain.adapter = adapter

        activityKreditBinding.btnSimpan.setOnClickListener {
            val transaksi = generateTransaksi()
            if (transaksi.transaksi.jumlah > 0)
                viewModel.saveTransaksi(transaksi)
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.kredit)
    }

    override fun tambah(type: UangType) {
        jumlah += type.tvalue
        activityKreditBinding.jumlahEdt.setText(jumlah.toString())
        val uang = items.find { it.type == type }
        if (uang != null) {
            uang.jumlah++
        }
    }

    override fun kurang(type: UangType) {
        if (jumlah > 0 && type.tvalue <= jumlah) {
            jumlah -= type.tvalue
            activityKreditBinding.jumlahEdt.setText(jumlah.toString())
            val uang = items.find { it.type == type }
            if (uang != null)
                uang.jumlah--
        }
    }

    private fun generateTransaksi(): TransaksiDetail {
        val tanggal = activityKreditBinding.tglEdt.text.toString().trim()
        val uang = items.filter { it.jumlah > 0 }
        val transaksi = Transaksi(jumlah = jumlah, tanggal = tanggal, type = TransaksiType.KREDIT.ordinal)
        return TransaksiDetail(transaksi, uang)
    }
}