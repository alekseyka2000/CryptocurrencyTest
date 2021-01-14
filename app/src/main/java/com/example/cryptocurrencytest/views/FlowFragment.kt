package com.example.cryptocurrencytest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.cryptocurrencytest.databinding.FragmentFlowBinding
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class FlowFragment : Fragment() {

    private var _binding: FragmentFlowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = binding.containerFlow.findNavController()

        NavigationUI.setupWithNavController(
            binding.navView,
            navController
        )
    }
}