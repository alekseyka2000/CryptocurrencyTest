package com.example.cryptocurrencytest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrencytest.R
import java.util.*
import kotlin.concurrent.schedule

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onStart() {
        super.onStart()
        Timer().schedule(2000) {
            parentFragmentManager.findFragmentById(R.id.container)?.findNavController()
                ?.navigate(R.id.action_startFragment_to_flowFragment)
        }
    }
}