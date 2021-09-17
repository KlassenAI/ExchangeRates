package com.android.exchangerates.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.exchangerates.databinding.ListItemBinding
import com.android.exchangerates.entities.Rate

class RateItemAdapter(
    private var rates: List<Rate>
) : RecyclerView.Adapter<RateItemAdapter.RateHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateHolder(binding)
    }

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        val rate = rates[position]
        holder.bind(rate)
    }

    override fun getItemCount(): Int = rates.size

    fun setList(list: List<Rate>) {
        rates = list
        Log.e("setList", rates.toString())
        notifyDataSetChanged()
    }

    class RateHolder(private val itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rate: Rate) {
            itemBinding.apply {
                itemName.text = rate.name
                itemCode.text = rate.charCode
                itemNominal.text = "%s %s".format("Номинал: ", rate.nominal)
                itemValue.text = "%s %s".format("Стоимость: ", rate.value)
            }
        }
    }
}