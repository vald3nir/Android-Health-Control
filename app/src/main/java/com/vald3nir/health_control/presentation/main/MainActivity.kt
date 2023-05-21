package com.vald3nir.health_control.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.vald3nir.auth.presentation.AuthActivity
import com.vald3nir.core_ui.animations.animateHeartBeat
import com.vald3nir.core_ui.animations.cancelAnimateHeartBeat
import com.vald3nir.health_control.databinding.ActivityMainBinding
import com.vald3nir.health_control.presentation.home.HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                openHomeActivity()
            } else {
                finish()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imvLogo.animateHeartBeat()
        openAuthActivity()
    }

    private fun openAuthActivity() {
        resultLauncher.launch(Intent(this, AuthActivity::class.java))
    }

    private fun openHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        binding.imvLogo.cancelAnimateHeartBeat()
        super.onDestroy()
    }
}