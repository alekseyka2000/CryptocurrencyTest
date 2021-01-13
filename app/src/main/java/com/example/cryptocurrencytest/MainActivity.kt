package com.example.cryptocurrencytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.cryptocurrencytest.databinding.ActivityMainBinding
import org.koin.core.component.KoinApiExtension
import java.util.Timer
import kotlin.concurrent.schedule

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = this.findNavController(R.id.container)
        navController.navigate(R.id.action_flowFragment_to_startFragment)

        Timer().schedule(2000) {
            navController.navigate(R.id.action_startFragment_to_flowFragment)
        }
    }
}