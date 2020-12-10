package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencytest.R
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CryptocurrencyListFragment : Fragment(), CellClickListener {

    private lateinit var viewModel: CryptocurrencyListViewModel
    private val contactAdapter = CryptocurrencyListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cryptocurrency_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(this@CryptocurrencyListFragment.context)
            adapter = contactAdapter
        }

        viewModel = ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())
            .get(CryptocurrencyListViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, { list ->
            contactAdapter.listCryptocurrencys = list
        })
        viewModel.fetchCryptocurencyData()
    }

    override fun onCellClickListener(name: String) {
        Log.d("log", name)
    }
}