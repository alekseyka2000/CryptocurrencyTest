package com.example.cryptocurrencytest.views.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptocurrencytest.R
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeFragment : Fragment() {

    private val exchangeViewModel: ExchangeViewModel by viewModel()
    private lateinit var subscriptions: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscriptions = CompositeDisposable()
        subscriptions.add(exchangeViewModel.fetchCryptocurencyData())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptions.clear()
    }
}