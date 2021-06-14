package com.djambulat69.gofileclient.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.ui.token.TokenActivity
import com.djambulat69.gofileclient.utils.getAccountSharedPreferences

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = getAccountSharedPreferences()
            .getString(
                getString(R.string.api_token_pref_key),
                null
            )


        startActivity(
            Intent(
                this,
                if (token == null) TokenActivity::class.java else MainActivity::class.java
            )
        )

        finish()
    }

}
