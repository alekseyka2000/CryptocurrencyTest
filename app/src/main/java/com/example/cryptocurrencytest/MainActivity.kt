package com.example.cryptocurrencytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencytest.databinding.ActivityMainBinding
import com.example.cryptocurrencytest.di.cryptocurrencyServiceModule
import com.example.cryptocurrencytest.di.viewModelModule
import com.example.cryptocurrencytest.views.StartFragment
import com.example.cryptocurrencytest.views.FlowFragment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import java.util.Timer
import kotlin.concurrent.schedule

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val startFragment by lazy { StartFragment() }
    private val flowFragment by lazy { FlowFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.container.id, startFragment)
            .commit()

        Timer().schedule(2000) {
            supportFragmentManager.beginTransaction()
                .remove(startFragment)
                .add(binding.container.id, flowFragment)
                .commit()
        }
    }
}