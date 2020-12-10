package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencytest.R
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import kotlin.properties.Delegates

class CryptocurrencyListAdapter(private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<CryptocurrencyListAdapter.CryptocurrencyViewHolder>() {

    var listCryptocurrencys: List<PrepareCryptocurrencyData> by Delegates.observable(emptyList()){
            _, oldValue, newValue ->
        notifyChanges(oldValue, newValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CryptocurrencyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        holder.bind(listCryptocurrencys[position])
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(listCryptocurrencys[position].cryptocurrencyName)
        }
    }

    override fun getItemCount(): Int = listCryptocurrencys.size

    class CryptocurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater
            .inflate(R.layout.cryptocurrency_item, parent, false)) {
        private var cryptocurrencyImageView: ImageView? = null
        private var cryptocurrencyNameText: TextView? = null
        private var cryptocurrencyPriceUSDText: TextView? = null

        init {
            cryptocurrencyImageView = itemView.findViewById(R.id.cryptocurrencyImage)
            cryptocurrencyNameText = itemView.findViewById(R.id.cryptocurrencyName)
            cryptocurrencyPriceUSDText = itemView.findViewById(R.id.cryptocurrencyPriceUDS)
        }

        fun bind(cryptocurrency: PrepareCryptocurrencyData) {
            cryptocurrencyImageView?.setImageResource(R.drawable.ic_bitcoin)
            cryptocurrencyNameText?.text = cryptocurrency.cryptocurrencyName
            cryptocurrencyPriceUSDText?.text = cryptocurrency.cryptocurrencyPriceUSD
        }
    }

    private fun notifyChanges(oldList: List<PrepareCryptocurrencyData>, newList: List<PrepareCryptocurrencyData>) {
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

interface CellClickListener {
    fun onCellClickListener(name: String)
}