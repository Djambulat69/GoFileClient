package com.djambulat69.gofileclient.ui.token

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ActivityTokenBinding
import com.djambulat69.gofileclient.network.AccountDetails
import com.djambulat69.gofileclient.ui.MainActivity
import com.djambulat69.gofileclient.utils.getAccountSharedPreferences
import com.djambulat69.gofileclient.utils.viewBinding
import io.reactivex.rxjava3.core.Observable

class TokenActivity : AppCompatActivity(), TokenView {

    private val viewModel: TokenViewModel by viewModels()

    private val binding by viewBinding { ActivityTokenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unbind()
    }

    override fun render(state: TokenState) {
        with(binding) {
            tokenProgressBar.isVisible = state.isLoading
            tokenInputLayout.isVisible = !state.isLoading
            if (state.error == null) {
                tokenInputLayout.error = null
            } else {
                tokenInputLayout.error = getString(R.string.smth_went_wrong)
            }
        }
    }

    override fun makeAction(action: TokenAction) {
        when (action) {
            is TokenAction.SaveDetailsAndStartMainActivity -> {
                saveDetailsAndStartMainActivity(action.details)
            }
        }
    }

    override fun loadTokenIntent(): Observable<String> {
        val actionDoneObservable = Observable.create<String> { emitter ->
            binding.tokenEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    emitter.onNext(binding.tokenEditText.text?.toString().orEmpty())

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        }

        val endIconObservable = Observable.create<String> { emitter ->
            binding.tokenInputLayout.setEndIconOnClickListener {
                pasteTextFromClipBoard()
                emitter.onNext(binding.tokenEditText.text?.toString().orEmpty())
            }
        }

        return Observable.merge(actionDoneObservable, endIconObservable)
    }

    private fun pasteTextFromClipBoard() {
        val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (
            clipBoard.hasPrimaryClip()
            &&
            clipBoard.primaryClipDescription?.hasMimeType("text/*") == true
        ) {
            binding.tokenEditText.setText(clipBoard.primaryClip?.getItemAt(0)?.text)
        }
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
