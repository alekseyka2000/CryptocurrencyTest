package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrencytest.R
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData

class CryptocurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val context: Context) :
    RecyclerView.ViewHolder(inflater
        .inflate(R.layout.cryptocurrency_item, parent, false)) {
    private var cryptocurrencyImageView: ImageView = itemView.findViewById(R.id.cryptocurrencyImage)
    private var cryptocurrencyNameText: TextView = itemView.findViewById(R.id.cryptocurrencyName)
    private var cryptocurrencyPriceUSDText: TextView = itemView.findViewById(R.id.cryptocurrencyPriceUDS)

    fun bind(cryptocurrency: PrepareCryptocurrencyData) {
        Glide
            .with(context)
            .load(cryptocurrency.imageReference)
            .into(cryptocurrencyImageView)
        cryptocurrencyNameText.text = cryptocurrency.cryptocurrencyName
        cryptocurrencyPriceUSDText.text = cryptocurrency.cryptocurrencyPriceUSD
    }
}