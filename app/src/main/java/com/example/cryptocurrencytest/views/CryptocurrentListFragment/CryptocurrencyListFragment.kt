package com.example.cryptocurrencytest.views.CryptocurrentListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrencytest.R
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CryptocurrencyListFragment : Fragment() {

    private lateinit var viewModel: CryptocurrencyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cryptocurrency_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())
            .get(CryptocurrencyListViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, { list ->
            view.findViewById<TextView>(R.id.list).text = list[1].name
        })
        viewModel.fetchCryptocurencyData()
    }
}