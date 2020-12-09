package com.example.cryptocurrencytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencytest.databinding.ActivityMainBinding
import com.example.cryptocurrencytest.di.cryptocurrencyServiceModule
import com.example.cryptocurrencytest.views.StartFragment
import com.example.cryptocurrencytest.views.flow.FlowFragment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import java.util.*
import kotlin.concurrent.schedule

@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent {

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

        initKoin()

//        val cryptocurrencyService by inject<CryptocurrencyService>()
//
//        val view: TextView = findViewById(R.id.text)
//        cryptocurrencyService.getCryptocurrency()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { view.text = it.data[1].name },
//                { view.text = it.message }
//            )

    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(cryptocurrencyServiceModule)
        }
    }
}