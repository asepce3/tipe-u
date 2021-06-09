package com.cahyana.asep.tipe_u.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cahyana.asep.tipe_u.data.entity.Transaksi
import com.cahyana.asep.tipe_u.data.entity.TransaksiType
import com.cahyana.asep.tipe_u.databinding.ItemMainBinding
import com.cahyana.asep.tipe_u.ui.detail.DetailActivity
import com.cahyana.asep.tipe_u.utils.CurrencyHelper

class MainAdapter : PagedListAdapter<Transaksi, MainAdapter.MainViewHolder>(DIFF_CALLBACK) {

    private lateinit var listener: ClickListener

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaksi>() {
            override fun areItemsTheSame(oldItem: Transaksi, newItem: Transaksi): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Transaksi, newItem: Transaksi): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val transaksi = getItem(position)
        if (transaksi != null)
            holder.bind(transaksi)
    }

    inner class MainViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transaksi) {
            with(binding) {
                tvJumlah.text = CurrencyHelper.toRupiah(item.jumlah)
                tvTanggal.text = item.tanggal
                tvSaldo.text = CurrencyHelper.toRupiah(item.saldo)
                typeTransaksi.text = if (item.type == TransaksiType.KREDIT.ordinal) "KREDIT" else "DEBIT"

                itemView.setOnClickListener {
                    listener.onClickListener(item.id)
                }
            }
        }
    }

    fun setOnClickListener(listener: ClickListener) {
        this.listener = listener
    }

    interface ClickListener {
        fun onClickListener(id: Int)
    }
}
