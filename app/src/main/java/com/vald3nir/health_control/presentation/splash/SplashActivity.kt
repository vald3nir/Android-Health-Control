package com.vald3nir.health_control.presentation.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import com.vald3nir.auth.presentation.AuthActivity
import com.vald3nir.core_ui.CoreActivity
import com.vald3nir.core_ui.animations.animateHeartBeat
import com.vald3nir.core_ui.animations.cancelAnimateHeartBeat
import com.vald3nir.health_control.databinding.ActivitySplashBinding
import com.vald3nir.health_control.presentation.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : CoreActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private val delay = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivitySplashBinding
    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            openHomeActivity()
        } else {
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imvLogo.animateHeartBeat()

        delay.postDelayed({
            openFlow()
        }, 2000L)

    }

    private fun openFlow() {
        if (viewModel.hasUserLogged()) {
            openHomeActivity()
        } else {
            resultLauncher.launch(Intent(this, AuthActivity::class.java))
        }
    }

    private fun openHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        delay.removeCallbacksAndMessages(null)
        binding.imvLogo.cancelAnimateHeartBeat()
        super.onDestroy()
    }
}