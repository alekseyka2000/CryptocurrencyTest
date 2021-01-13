package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencytest.databinding.FragmentCryptocurrencyListBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CryptocurrencyListFragment : Fragment(), CellClickListener {

    private var _binding: FragmentCryptocurrencyListBinding? = null
    private val binding get() = _binding!!
    private val cryptocurrencyViewModel: CryptocurrencyListViewModel by viewModel()
    private val contactAdapter = CryptocurrencyListAdapter(this)
    private lateinit var subscriptions: CompositeDisposable

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

        subscriptions = CompositeDisposable()

//        binding.list.apply {
//            layoutManager = LinearLayoutManager(this@CryptocurrencyListFragment.context)
//            adapter = contactAdapter
//        }
        cryptocurrencyViewModel.liveData.observe(viewLifecycleOwner, { list ->
            contactAdapter.listCryptocurrencys = list
        })
        subscriptions.add(cryptocurrencyViewModel.fetchCryptocurencyData())
    }

    override fun onCellClickListener(name: String) {
        Log.d("log", name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptions.clear()
        _binding = null
    }
}