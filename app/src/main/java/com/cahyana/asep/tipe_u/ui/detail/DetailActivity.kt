package com.cahyana.asep.tipe_u.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.tipe_u.R
import com.cahyana.asep.tipe_u.data.entity.Saldo
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.Uang
import com.cahyana.asep.tipe_u.data.entity.UangType
import com.cahyana.asep.tipe_u.databinding.ActivityDetailBinding
import com.cahyana.asep.tipe_u.ui.main.MainViewModel
import com.cahyana.asep.tipe_u.utils.CurrencyHelper
import com.cahyana.asep.tipe_u.utils.DateUtils
import com.cahyana.asep.tipe_u.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        val adapter = DetailAdapter()
        binding.rvMain.adapter = adapter

        val data = intent.getIntExtra(EXTRA_ID, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        if (data != 0) {
            viewModel.getTransaksiDetail(data).observe(this, {
                adapter.setItems(it.uang)
                adapter.notifyDataSetChanged()
                populateView(it.transaksi)
            })
        } else {
            viewModel.getSaldoDetail().observe(this, {
                val uang = generateListUang(it)
                adapter.setItems(uang)
                adapter.notifyDataSetChanged()
                populateView(it)
            })
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.detail)
    }

    private fun populateView(item: Transaksi) {
        binding.tvJumlah.text = CurrencyHelper.toRupiah(item.jumlah)
        binding.tvTgl.text = item.tanggal
    }

    private fun populateView(saldo: Saldo) {
        binding.tvJumlah.text = CurrencyHelper.toRupiah(saldo.saldo)
        binding.tvTgl.text = DateUtils.getCurrentDate()
    }

    private fun generateListUang(saldo: Saldo): List<Uang> {
        val uang = arrayListOf<Uang>()
        uang.apply {
            if (saldo.u100k != 0)
                add(Uang(type = UangType.T_100K, jumlah = saldo.u100k))

            if (saldo.u50k != 0)
                add(Uang(type = UangType.T_50K, jumlah = saldo.u50k))

            if (saldo.u20k != 0)
                add(Uang(type = UangType.T_20K, jumlah = saldo.u20k))

            if (saldo.u10k != 0)
                add(Uang(type = UangType.T_10K, jumlah = saldo.u10k))

            if (saldo.u5k != 0)
                add(Uang(type = UangType.T_5K, jumlah = saldo.u5k))

            if (saldo.u2k != 0)
                add(Uang(type = UangType.T_2K, jumlah = saldo.u2k))

            if (saldo.u1k != 0)
                add(Uang(type = UangType.T_1K, jumlah = saldo.u1k))

            if (saldo.u5 != 0)
                add(Uang(type = UangType.T_500, jumlah = saldo.u5))

            if (saldo.u2 != 0)
                add(Uang(type = UangType.T_200, jumlah = saldo.u2))

            if (saldo.u1 != 0)
                add(Uang(type = UangType.T_100, jumlah = saldo.u1))
        }
        return uang
    }
}