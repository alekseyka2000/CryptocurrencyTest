package com.example.cryptocurrencytest.views.cryptocurrentlistfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencytest.R
import com.example.cryptocurrencytest.model.entity.Data
import kotlin.properties.Delegates

class CryptocurrencyListAdapter(private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<CryptocurrencyListAdapter.CryptocurrencyViewHolder>() {

    var listCryptocurrencys: List<Data> by Delegates.observable(emptyList()){
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
            cellClickListener.onCellClickListener(listCryptocurrencys[position].name)
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

        fun bind(cryptocurrency: Data) {
            cryptocurrencyImageView?.setImageResource(R.drawable.ic_bitcoin)
            cryptocurrencyNameText?.text = cryptocurrency.name
            cryptocurrencyPriceUSDText?.text = cryptocurrency.total_supply.toString()
        }
    }

    private fun notifyChanges(oldList: List<Data>, newList: List<Data>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
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