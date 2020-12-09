package com.example.cryptocurrencytest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.cryptocurrencytest.databinding.ActivityMainBinding
import com.example.cryptocurrencytest.di.cryptocurrencyServiceModule
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.views.StartFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
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
    private val startFragment by lazy {StartFragment()}

    @SuppressLint("CheckResult")
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