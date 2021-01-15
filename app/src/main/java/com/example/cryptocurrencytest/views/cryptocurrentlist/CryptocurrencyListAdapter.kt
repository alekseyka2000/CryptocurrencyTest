package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import kotlin.properties.Delegates

class CryptocurrencyListAdapter(private val cellClickListener: (String) -> Unit, private val context: Context) :
    RecyclerView.Adapter<CryptocurrencyViewHolder>() {

    var listCryptocurrencys: List<PrepareCryptocurrencyData> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        notifyChanges(oldValue, newValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CryptocurrencyViewHolder(inflater, parent, context)
    }

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        holder.bind(listCryptocurrencys[position])
        holder.itemView.setOnClickListener {
            cellClickListener(listCryptocurrencys[position].cryptocurrencyName)
        }
    }

    override fun getItemCount(): Int = listCryptocurrencys.size

    private fun notifyChanges(
        oldList: List<PrepareCryptocurrencyData>,
        newList: List<PrepareCryptocurrencyData>
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].cryptocurrencyName == newList[newItemPosition].cryptocurrencyName
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }
}