package com.example.cryptocurrencytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.koin.core.component.KoinApiExtension
import java.util.Timer
import kotlin.concurrent.schedule

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Timer().schedule(2000) {
            this@MainActivity.findNavController(R.id.container)
                .navigate(R.id.action_startFragment_to_flowFragment)
        }
    }
}