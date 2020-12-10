package com.example.cryptocurrencytest.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptocurrencytest.R
import com.example.cryptocurrencytest.databinding.FragmentFlowBinding
import com.example.cryptocurrencytest.views.cryptocurrentlist.CryptocurrencyListFragment
import com.example.cryptocurrencytest.views.exchange.ExchangeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class FlowFragment : Fragment() {

    private lateinit var binding: FragmentFlowBinding
    private val listFragment by lazy { CryptocurrencyListFragment() }
    private val exchangeFragment by lazy { ExchangeFragment() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_flow, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFlowBinding.inflate(layoutInflater)

        childFragmentManager.beginTransaction()
            .add(binding.container.id, listFragment)
            .add(binding.container.id, exchangeFragment)
            .hide(exchangeFragment)
            .commit()

        view.findViewById<BottomNavigationView>(R.id.navView)
            .setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_list -> {
                        childFragmentManager.beginTransaction()
                            .show(listFragment)
                            .hide(exchangeFragment)
                            .commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_exchange -> {
                        childFragmentManager.beginTransaction()
                            .show(exchangeFragment)
                            .hide(listFragment)
                            .commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> false
                }
            }
    }
}