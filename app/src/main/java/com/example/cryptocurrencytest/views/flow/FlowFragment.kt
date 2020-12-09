package com.example.cryptocurrencytest.views.flow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptocurrencytest.R
import com.example.cryptocurrencytest.databinding.FragmentFlowBinding

class FlowFragment : Fragment() {

    private val binding by lazy { FragmentFlowBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navigation_movies_list ->{
                    //showFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite_actors ->{
                    //showFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}