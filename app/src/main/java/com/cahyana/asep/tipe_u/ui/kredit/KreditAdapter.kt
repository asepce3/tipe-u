package com.cahyana.asep.tipe_u.ui.kredit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cahyana.asep.tipe_u.data.entity.Uang
import com.cahyana.asep.tipe_u.data.entity.UangType
import com.cahyana.asep.tipe_u.databinding.ItemKreditBinding

class KreditAdapter : RecyclerView.Adapter<KreditAdapter.DebitAdapterViewHolder>(){

    private lateinit var kreditListener: KreditListener
    private val items = arrayListOf<Uang>()

    fun setItems(items: List<Uang>?) {
        if (items != null) {
            this.items.clear()
            this.items.addAll(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebitAdapterViewHolder {
        val binding = ItemKreditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DebitAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DebitAdapterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class DebitAdapterViewHolder(private val binding: ItemKreditBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Uang) {
            with(binding) {
                tvTitle.text = item.type.tname
                tvJumlah.text = item.jumlah.toString()

                btnAdd.setOnClickListener {
                    val value = tvJumlah.text.trim().toString()
                    tvJumlah.text = tambah(value)
                    kreditListener.tambah(item.type)
                }

                btnMin.setOnClickListener {
                    val value = tvJumlah.text.trim().toString()
                    if (value != "0") {
                        tvJumlah.text = kurang(value)
                        kreditListener.kurang(item.type)
                    }
                }
            }


        }

        private fun tambah(value: String) = (Integer.parseInt(value) + 1).toString()

        private fun kurang(value: String): String = (Integer.parseInt(value) - 1).toString()
    }

    fun setKreditListener(listener: KreditListener) {
        this.kreditListener = listener
    }

    interface KreditListener {
        fun tambah(type: UangType)
        fun kurang(type: UangType)
    }


}