package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencytest.databinding.FragmentCryptocurrencyListBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CryptocurrencyListFragment : Fragment() {

    private var _binding: FragmentCryptocurrencyListBinding? = null
    private val binding get() = _binding!!
    private val cryptocurrencyViewModel: CryptocurrencyListViewModel by viewModel()
    private lateinit var contactAdapter: CryptocurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptocurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactAdapter = CryptocurrencyListAdapter ({ a: String -> itemClick(a) }, activity as Context)

        binding.list.apply {
            layoutManager = LinearLayoutManager(this@CryptocurrencyListFragment.context)
            adapter = contactAdapter
        }
        Log.d("Creation order", "F")
        cryptocurrencyViewModel.cryptocurrencyLiveData.observe(viewLifecycleOwner, { list ->
            contactAdapter.listCryptocurrencys = list
        })
        Log.d("Creation order", "F2")
    }

    private fun itemClick(name: String) {
        Log.d("log", name)
    }
}