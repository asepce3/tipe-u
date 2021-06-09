package com.cahyana.asep.tipe_u.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.tipe_u.R
import com.cahyana.asep.tipe_u.databinding.ActivityMainBinding
import com.cahyana.asep.tipe_u.ui.debit.DebitActivity
import com.cahyana.asep.tipe_u.ui.detail.DetailActivity
import com.cahyana.asep.tipe_u.ui.kredit.KreditActivity
import com.cahyana.asep.tipe_u.utils.CurrencyHelper
import com.cahyana.asep.tipe_u.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), MainAdapter.ClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val adapter = MainAdapter()
        adapter.setOnClickListener(this)
        activityMainBinding.rvMain.adapter = adapter
        activityMainBinding.rvMain.layoutManager = LinearLayoutManager(this)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.getSaldo().observe(this, {
            if (it != null)
                activityMainBinding.tvSaldo.text = CurrencyHelper.toRupiah(it)
        })
        viewModel.getAllTransaksi().observe(this, {
            println(it)
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this@MainActivity).inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.options_kredit -> {
                val intentKredit = Intent(this@MainActivity, KreditActivity::class.java)
                startActivity(intentKredit)
            }
            R.id.options_debit -> {
                val intentDebit = Intent(this@MainActivity, DebitActivity::class.java)
                startActivity(intentDebit)
            }
        }
        return true
    }

    override fun onClickListener(id: Int) {
        val intentTransaksiDetail = Intent(this, DetailActivity::class.java)
        intentTransaksiDetail.putExtra(DetailActivity.EXTRA_ID, id)
        startActivity(intentTransaksiDetail)
    }
}