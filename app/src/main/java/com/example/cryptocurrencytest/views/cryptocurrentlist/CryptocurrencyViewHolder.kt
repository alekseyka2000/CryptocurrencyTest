package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencytest.R
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData

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