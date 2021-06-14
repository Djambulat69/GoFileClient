package com.djambulat69.gofileclient.ui.token

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.edit
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ActivityTokenBinding
import com.djambulat69.gofileclient.network.AccountDetails
import com.djambulat69.gofileclient.network.TokenInterceptor
import com.djambulat69.gofileclient.ui.MainActivity
import com.djambulat69.gofileclient.utils.Data
import com.djambulat69.gofileclient.utils.getAccountSharedPreferences
import com.djambulat69.gofileclient.utils.process

class TokenActivity : AppCompatActivity() {

    private val viewModel: TokenViewModel by viewModels()

    private var _binding: ActivityTokenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.accountDetails.observe(this, ::saveDetailsAndStartMainActivity)

        val editText = findViewById<AppCompatEditText>(R.id.token_edit_text)

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                val enteredToken = editText.text?.toString().orEmpty()
                TokenInterceptor.token = enteredToken
                viewModel.getAccountDetails()

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun saveDetailsAndStartMainActivity(detailsData: Data<AccountDetails>) {
        detailsData.process(
            { Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show() },
            { details ->
                getAccountSharedPreferences().edit {
                    putString(
                        getString(R.string.api_token_pref_key),
                        details.token
                    )
                    putString(
                        getString(R.string.root_folder_pref_key),
                        details.rootFolder
                    )
                }

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                finish()
            },
            { Toast.makeText(this, "Error token", Toast.LENGTH_SHORT).show() }
        )
    }

}
