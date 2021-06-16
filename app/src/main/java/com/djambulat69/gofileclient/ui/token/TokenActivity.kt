package com.djambulat69.gofileclient.ui.token

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ActivityTokenBinding
import com.djambulat69.gofileclient.network.AccountDetails
import com.djambulat69.gofileclient.network.TokenInterceptor
import com.djambulat69.gofileclient.ui.MainActivity
import com.djambulat69.gofileclient.utils.Data
import com.djambulat69.gofileclient.utils.getAccountSharedPreferences
import com.djambulat69.gofileclient.utils.process
import com.djambulat69.gofileclient.utils.viewBinding

class TokenActivity : AppCompatActivity() {

    private val viewModel: TokenViewModel by viewModels()

    private val binding by viewBinding { ActivityTokenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.accountDetails.observe(this, ::processDetailsData)

        binding.tokenInputLayout.setEndIconOnClickListener {
            pasteTextFromClipBoard()
        }

        binding.tokenEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                getAccountDetails()

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun getAccountDetails() {
        val enteredToken = binding.tokenEditText.text?.toString().orEmpty()
        TokenInterceptor.token = enteredToken
        viewModel.getAccountDetails()
    }

    private fun pasteTextFromClipBoard() {
        val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (
            clipBoard.hasPrimaryClip()
            &&
            clipBoard.primaryClipDescription?.hasMimeType("text/*") == true
        ) {
            binding.tokenEditText.setText(clipBoard.primaryClip?.getItemAt(0)?.text)

            getAccountDetails()
        }
    }

    private fun processDetailsData(detailsData: Data<AccountDetails>) {
        detailsData.process(
            { Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show() },
            { details -> saveDetailsAndStartMainActivity(details) },
            { Toast.makeText(this, "Error token", Toast.LENGTH_SHORT).show() }
        )
    }

    private fun saveDetailsAndStartMainActivity(details: AccountDetails) {
        getAccountSharedPreferences().edit {
            putString(getString(R.string.api_token_pref_key), details.token)
            putString(getString(R.string.root_folder_pref_key), details.rootFolder)
        }

        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }
}
