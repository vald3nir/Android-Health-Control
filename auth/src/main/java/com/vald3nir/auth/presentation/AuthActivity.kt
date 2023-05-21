package com.vald3nir.auth.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.vald3nir.auth.R
import com.vald3nir.core_ui.CoreActivity

class AuthActivity : CoreActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    fun closeAuthFlow() {
        val data = Intent()
        val text = "Result to be returned...."
        data.data = Uri.parse(text)
        setResult(RESULT_OK, data)
        finish()
    }
}