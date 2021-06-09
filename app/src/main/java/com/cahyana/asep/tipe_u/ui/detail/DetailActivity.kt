package com.cahyana.asep.tipe_u.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.tipe_u.R
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.databinding.ActivityDetailBinding
import com.cahyana.asep.tipe_u.ui.main.MainViewModel
import com.cahyana.asep.tipe_u.utils.CurrencyHelper
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
                populateView(binding, it.transaksi)
            })
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.detail)
    }

    private fun populateView(binding: ActivityDetailBinding, item: Transaksi) {
        binding.tvJumlah.text = CurrencyHelper.toRupiah(item.jumlah)
        binding.tvTgl.text = item.tanggal
    }
}