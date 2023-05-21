package com.vald3nir.health_control.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.vald3nir.core_ui.animations.animateHeartBeat
import com.vald3nir.core_ui.animations.cancelAnimateHeartBeat
import com.vald3nir.health_control.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    fun openSomeActivityForResult() {
//        val intent = Intent(this, SomeActivity::class.java)
//        resultLauncher.launch(intent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
//            doSomeOperations()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imvLogo.animateHeartBeat()
    }

    override fun onDestroy() {
        binding.imvLogo.cancelAnimateHeartBeat()
        super.onDestroy()
    }
}