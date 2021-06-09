package com.cahyana.asep.tipe_u.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cahyana.asep.tipe_u.data.entity.Uang
import com.cahyana.asep.tipe_u.databinding.ItemDetailBinding
import com.cahyana.asep.tipe_u.utils.CurrencyHelper

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailAdapterViewHolder>() {

    private val items = arrayListOf<Uang>()

    fun setItems(items: List<Uang>?) {
        if (items != null) {
            this.items.clear()
            this.items.addAll(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapterViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailAdapterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class DetailAdapterViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Uang) {
            with(binding) {
                tvTitle.text = item.type.tname
                tvJumlah.text = item.jumlah.toString()
                tvTotal.text = CurrencyHelper.toRupiah((item.type.tvalue * item.jumlah).toLong())
            }
        }
    }
}