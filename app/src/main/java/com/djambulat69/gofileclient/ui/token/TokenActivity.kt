package com.djambulat69.gofileclient.ui.token

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ActivityTokenBinding
import com.djambulat69.gofileclient.ui.MainActivity
import com.djambulat69.gofileclient.utils.MIME_TYPE_TEXT_ALL
import com.djambulat69.gofileclient.utils.getAccountSharedPreferences

class TokenActivity : AppCompatActivity() {

    private val viewModel: TokenViewModel by viewModels()

    private val binding by lazy { ActivityTokenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    private fun pasteTextFromClipBoard() {
        val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (
            clipBoard.hasPrimaryClip()
            &&
            clipBoard.primaryClipDescription?.hasMimeType(MIME_TYPE_TEXT_ALL) == true
        ) {
            binding.tokenEditText.setText(clipBoard.primaryClip?.getItemAt(0)?.text)
        }
    }

    private fun saveTokenAndQuite(token: String) {
        getAccountSharedPreferences().edit {
            putString(getString(R.string.api_token_pref_key), token)
        }

        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }
}
